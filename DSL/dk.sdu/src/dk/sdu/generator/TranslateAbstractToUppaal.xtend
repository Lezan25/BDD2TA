package dk.sdu.generator

import dk.sdu.model.AbstractModel
import dk.sdu.model.AbstractModel.BetweenGuard
import dk.sdu.model.AbstractModel.ClockEvent
import dk.sdu.model.AbstractModel.ConjunctionGuard
import dk.sdu.model.AbstractModel.Effect
import dk.sdu.model.AbstractModel.Entity
import dk.sdu.model.AbstractModel.Event
import dk.sdu.model.AbstractModel.Guard
import dk.sdu.model.AbstractModel.Model
import dk.sdu.model.AbstractModel.NumberValue
import dk.sdu.model.AbstractModel.NumericProperty
import dk.sdu.model.AbstractModel.PropAssign
import dk.sdu.model.AbstractModel.PropComparison
import dk.sdu.model.AbstractModel.PropDelta
import dk.sdu.model.AbstractModel.PropRandom
import dk.sdu.model.AbstractModel.PropValue
import dk.sdu.model.AbstractModel.Scenario
import dk.sdu.model.AbstractModel.SequenceEffect
import dk.sdu.model.AbstractModel.StateAssign
import dk.sdu.model.AbstractModel.StateEquality
import dk.sdu.model.AbstractModel.StateInequality
import dk.sdu.model.AbstractModel.StatesProperty
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashSet
import java.util.List

class TranslateAbstractToUppaal {

    var int idCounter = 0
    val locationIds   = new HashMap<String, String>()
    val receiverEdges = new HashMap<String, List<String>>()

    def String generate(Model m) {
        idCounter = 0
        locationIds.clear()
        receiverEdges.clear()

        for (e : m.entities) {
            val statesProp = e.properties.filter(StatesProperty).head
            for (s : statesProp.stateNames)
                locationIds.put(locKey(e.name, s), nextId())
            for (sc : m.scenarios.filter[sc | hasOnEnd(sc) && onEndEntity(sc) == e.name])
                locationIds.put(finishingLocKey(e.name, sc), nextId())
            receiverEdges.put(e.name, new ArrayList<String>)
        }

        val globalDecls = generateGlobalDecls(m)
        val templates   = m.entities.map[generateTemplate(it, m)].join("\n")
        val instantiations = m.entities.map[e | e.name + "_1 = " + e.name + "();"].join("\n")
        val systemLine     = m.entities.map[e | e.name + "_1"].join(", ")

        '''<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE nta PUBLIC '-//Uppaal Team//DTD Flat System 1.6//EN' 'http://www.it.uu.se/research/group/darts/uppaal/flat-1_6.dtd'>
<nta>
	<declaration>«globalDecls»</declaration>
«templates»
	<system>
«instantiations»
system «systemLine»;
	</system>
	<queries>
«generateQueries(m)»	</queries>
</nta>'''
    }

    def private String generateGlobalDecls(Model m) {
        val sb = new StringBuilder
        for (e : m.entities) {
            val statesProp = e.properties.filter(StatesProperty).head
            val stateNames = statesProp.stateNames
            val typeName   = e.name + "_state_type"

            sb.append("typedef int[0," + (stateNames.size - 1) + "] " + typeName + ";\n")
            for (var int i = 0; i < stateNames.size; i++)
                sb.append("const " + typeName + " " + stateConst(e.name, stateNames.get(i)) + " = " + i + ";\n")
            sb.append("int " + stateVar(e.name) + " = " + stateConst(e.name, stateNames.get(0)) + ";\n")
            for (p : e.properties.filter(NumericProperty))
                sb.append("int " + propVar(e.name, p.name) + " = " + p.value + ";\n")
            sb.append("\n")
        }

        for (sc : m.scenarios) {
            val e0       = ownerOf(sc)
            val onEndEnt = if (hasOnEnd(sc)) onEndEntity(sc) else null

            if (onEndEnt !== null && e0 != onEndEnt)
                sb.append("broadcast chan " + sc.name + "_trigger;\n")

            val nonEStateAssigns = if (onEndEnt !== null)
                collectStateAssigns(sc.effect).filter[sa | sa.entity != onEndEnt].toList
            else
                collectStateAssigns(sc.effect).filter[sa | sa.entity != e0].toList

            if (!nonEStateAssigns.empty)
                sb.append("broadcast chan " + sc.name + ";\n")
        }

        sb.toString
    }

    def private String generateTemplate(Entity e, Model m) {
        val statesProp     = e.properties.filter(StatesProperty).head
        val stateNames     = statesProp.stateNames
        val ownedScenarios = m.scenarios.filter[sc | ownerOf(sc) == e.name].toList
        val onEndScenarios = m.scenarios.filter[sc | hasOnEnd(sc) && onEndEntity(sc) == e.name].toList
        val regularScenarios = ownedScenarios.filter[sc | !hasOnEnd(sc)].toList

        // Collect all distinct positive clock amounts used by scenarios owned by e
        val clockValues = new LinkedHashSet<Integer>
        for (sc : ownedScenarios)
            if (sc.event.clock !== null && sc.event.clock.amount > 0)
                clockValues.add(sc.event.clock.amount)

        val allClockResets = clockValues.map[v | clockName(v) + " = 0"].join(", ")

        val locations = new ArrayList<String>
        val edges     = new ArrayList<String>

        var x = 0
        for (s : stateNames) {
            val isUrgent  = isUrgentLocation(e.name, s, ownedScenarios)
            val invariant = getLocationInvariant(e.name, s, ownedScenarios)
            locations.add(buildLocation(locationIds.get(locKey(e.name, s)), s, x, isUrgent, invariant))
            x += 200
        }

        for (sc : onEndScenarios) {
            val finId    = locationIds.get(finishingLocKey(e.name, sc))
            val srcState = onEndState(sc)
            val invariant = getLocationInvariant(e.name, srcState, ownedScenarios)
            locations.add(buildLocation(finId, srcState + "_finishing_" + sc.name, x, false, invariant))
            x += 200
        }

        val initLocId = locationIds.get(locKey(e.name, stateNames.get(0)))

        // Regular scenarios where e is owner
        for (sc : regularScenarios) {
            val sourceState  = getSourceState(sc.guard, e.name)
            val sourceLocId  = locationIds.get(locKey(e.name, sourceState))
            val primaryAssign = collectStateAssigns(sc.effect).findFirst[sa | sa.entity == e.name]
            val targetState  = if (primaryAssign !== null) primaryAssign.state else sourceState
            val targetLocId  = locationIds.get(locKey(e.name, targetState))
            val affectedNonE = collectStateAssigns(sc.effect).filter[sa | sa.entity != e.name].toList
            val needsChan    = !affectedNonE.empty
            val syncStr      = if (needsChan) sc.name + "!" else null
            val guardStr     = buildGuard(sc.guard, sc.event, e.name)
            val updateStr    = buildUpdate(sc.effect, e.name, allClockResets)
            val selectStr    = buildSelect(sc.effect)

            edges.add(buildTransition(nextId(), sourceLocId, targetLocId, selectStr, guardStr, syncStr, updateStr))

            if (needsChan)
                for (sa : affectedNonE) {
                    val recvList = receiverEdges.get(sa.entity)
                    if (recvList !== null)
                        addReceiverEdgesForEntity(sc, sa.entity, recvList, m)
                }
        }

        // Self-loop edges for clock reset when a clock guard condition is not satisfied
        for (s : stateNames) {
            val clocksForState = ownedScenarios
                .filter[sc | sc.event.clock !== null && sc.event.clock.amount > 0 && getSourceState(sc.guard, e.name) == s]
                .map[sc | sc.event.clock.amount]
                .toSet

            for (amount : clocksForState) {
                val scenariosForClock = ownedScenarios.filter[sc |
                    sc.event.clock !== null && sc.event.clock.amount == amount && getSourceState(sc.guard, e.name) == s
                ].toList

                val nonClockGuards = scenariosForClock
                    .map[sc | buildGuardExpr(sc.guard, e.name)]
                    .filter[g | !g.empty]
                    .toList

                if (!nonClockGuards.empty) {
                    val negated = if (nonClockGuards.size == 1)
                        "!((" + nonClockGuards.head + "))"
                    else
                        "!((" + nonClockGuards.join(") || (") + "))"

                    val selfGuard = clockName(amount) + " == " + amount + " && " + negated
                    val selfUpdate = clockName(amount) + " = 0"
                    val locId = locationIds.get(locKey(e.name, s))
                    edges.add(buildTransition(nextId(), locId, locId, null, selfGuard, null, selfUpdate))
                }
            }
        }

        // on_end scenarios where e is the finishing entity
        for (sc : onEndScenarios) {
            val e0       = ownerOf(sc)
            val srcState = onEndState(sc)
            val srcLocId = locationIds.get(locKey(e.name, srcState))
            val finLocId = locationIds.get(finishingLocKey(e.name, sc))
            val ownerIsE = e0 == e.name

            if (ownerIsE) {
                val guardStr  = buildGuard(sc.guard, sc.event, e.name)
                val selectStr = buildSelect(sc.effect)
                edges.add(buildTransition(nextId(), srcLocId, finLocId, selectStr, guardStr, null, null))
            } else {
                edges.add(buildTransition(nextId(), srcLocId, finLocId, null, null, sc.name + "_trigger?", null))
            }

            val exitingScenarios = m.scenarios.filter[s2 |
                ownerOf(s2) == e.name && getSourceState(s2.guard, e.name) == srcState
            ].toList

            val affectedNonE = collectStateAssigns(sc.effect).filter[sa | sa.entity != e.name].toList
            val needsChan    = !affectedNonE.empty

            for (sc2 : exitingScenarios) {
                val mergedEffect  = mergeEffects(sc2.effect, sc.effect, e.name)
                val primaryAssign = collectStateAssigns(mergedEffect).findFirst[sa | sa.entity == e.name]
                val targetState   = if (primaryAssign !== null) primaryAssign.state else srcState
                val targetLocId   = locationIds.get(locKey(e.name, targetState))
                val guardStr      = buildGuard(sc2.guard, sc2.event, e.name)
                val updateStr     = buildUpdate(mergedEffect, e.name, allClockResets)
                val selectStr     = buildSelect(mergedEffect)
                val syncStr       = if (needsChan) sc.name + "!" else null
                edges.add(buildTransition(nextId(), finLocId, targetLocId, selectStr, guardStr, syncStr, updateStr))
            }

            if (needsChan)
                for (sa : affectedNonE) {
                    val recvList = receiverEdges.get(sa.entity)
                    if (recvList !== null)
                        addReceiverEdgesForEntity(sc, sa.entity, recvList, m)
                }
        }

        // on_end scenarios where e is owner but not finishing entity
        for (sc : ownedScenarios.filter[sc | hasOnEnd(sc) && onEndEntity(sc) != e.name]) {
            val sourceState = getSourceState(sc.guard, e.name)
            val sourceLocId = locationIds.get(locKey(e.name, sourceState))
            val guardStr    = buildGuard(sc.guard, sc.event, e.name)
            edges.add(buildTransition(nextId(), sourceLocId, sourceLocId, null, guardStr, sc.name + "_trigger!", allClockResets))
        }

        edges.addAll(receiverEdges.get(e.name))

        val clockDecl = if (clockValues.empty)
            "clock c_0;"
        else
            clockValues.map[v | "clock " + clockName(v) + ";"].join(" ")

        '''	<template>
		<name>«e.name»</name>
		<declaration>«clockDecl»</declaration>
«locations.join("\n")»
		<init ref="«initLocId»"/>
«edges.join("\n")»
	</template>'''
    }

    def private void addReceiverEdgesForEntity(Scenario sc, String entityName, List<String> out, Model m) {
        val entity      = m.entities.findFirst[e | e.name == entityName]
        val statesProp  = entity.properties.filter(StatesProperty).head
        val sa          = collectStateAssigns(sc.effect).findFirst[s | s.entity == entityName]
        if (sa === null) return
        val targetLocId = locationIds.get(locKey(entityName, sa.state))

        val receiverClockValues = m.scenarios
            .filter[s | ownerOf(s) == entityName && s.event.clock !== null && s.event.clock.amount > 0]
            .map[s | s.event.clock.amount]
            .toSet
        val clockResets = receiverClockValues.map[v | clockName(v) + " = 0"].join(", ")
        val updateStr   = stateVar(entityName) + " = " + stateConst(entityName, sa.state) +
                          (if (!clockResets.empty) ", " + clockResets else "")

        for (s : statesProp.stateNames) {
            val fromLocId = locationIds.get(locKey(entityName, s))
            out.add(buildTransition(nextId(), fromLocId, targetLocId, null, null, sc.name + "?", updateStr))
        }
    }

    def private Effect mergeEffects(Effect sc2Effect, Effect scEffect, String entityName) {
        val sc2Parts = flattenEffect(sc2Effect)
        val scParts  = flattenEffect(scEffect)
        val scProps  = scParts.filter(PropAssign).map[ref.prop].toSet
        val scDeltas = scParts.filter(PropDelta).map[ref.prop].toSet
        val scHasStateAssign = scParts.filter(StateAssign).exists[entity == entityName]

        val kept = new ArrayList<Effect>
        for (part : sc2Parts) {
            switch part {
                StateAssign: if (part.entity == entityName && scHasStateAssign) {} else kept.add(part)
                PropAssign:  if (scProps.contains(part.ref.prop)) {} else kept.add(part)
                PropDelta:   if (scDeltas.contains(part.ref.prop)) {} else kept.add(part)
                default:     kept.add(part)
            }
        }
        for (part : scParts)
            kept.add(part)

        kept.reduce[acc, f | new SequenceEffect(acc, f)]
    }

    def private List<Effect> flattenEffect(Effect e) {
        val result = new ArrayList<Effect>
        switch e {
            SequenceEffect: {
                result.addAll(flattenEffect(e.left))
                result.addAll(flattenEffect(e.right))
            }
            default: result.add(e)
        }
        result
    }

    def private Effect collectEffectsForEntity(Effect e, String entityName) {
        val parts = flattenEffect(e).filter[part |
            switch part {
                StateAssign: part.entity == entityName
                PropAssign:  part.ref.entity == entityName
                PropDelta:   part.ref.entity == entityName
                PropRandom:  part.ref.entity == entityName
                default: false
            }
        ].toList
        if (parts.empty) null
        else parts.reduce[acc, f | new SequenceEffect(acc, f)]
    }

    def private String buildLocation(String locId, String name, int x, boolean urgent, String invariant) {
        val sb = new StringBuilder
        sb.append("\t\t<location id=\"" + locId + "\" x=\"" + x + "\" y=\"0\">\n")
        sb.append("\t\t\t<name x=\"" + (x - 10) + "\" y=\"-30\">" + name + "</name>\n")
        if (urgent)
            sb.append("\t\t\t<committed/>\n")
        else if (invariant !== null && !invariant.empty)
            sb.append("\t\t\t<label kind=\"invariant\" x=\"" + (x - 10) + "\" y=\"15\">" + xmlEscape(invariant) + "</label>\n")
        sb.append("\t\t</location>")
        sb.toString
    }

    def private String buildTransition(String edgeId, String sourceId, String targetId,
                                       String selectStr, String guardStr, String syncStr, String updateStr) {
        val sb = new StringBuilder
        sb.append("\t\t<transition id=\"" + edgeId + "\">\n")
        sb.append("\t\t\t<source ref=\"" + sourceId + "\"/>\n")
        sb.append("\t\t\t<target ref=\"" + targetId + "\"/>\n")
        if (selectStr !== null && !selectStr.empty)
            sb.append("\t\t\t<label kind=\"select\" x=\"0\" y=\"-60\">" + selectStr + "</label>\n")
        if (guardStr !== null && !guardStr.empty)
            sb.append("\t\t\t<label kind=\"guard\" x=\"0\" y=\"-40\">" + xmlEscape(guardStr) + "</label>\n")
        if (syncStr !== null && !syncStr.empty)
            sb.append("\t\t\t<label kind=\"synchronisation\" x=\"0\" y=\"-20\">" + syncStr + "</label>\n")
        if (updateStr !== null && !updateStr.empty)
            sb.append("\t\t\t<label kind=\"assignment\" x=\"0\" y=\"0\">" + xmlEscape(updateStr) + "</label>\n")
        sb.append("\t\t</transition>")
        sb.toString
    }

    def private String buildGuard(Guard g, Event ev, String e0) {
        val parts = new ArrayList<String>
        if (ev.clock !== null && ev.clock.amount > 0)
            parts.add(clockName(ev.clock.amount) + " == " + ev.clock.amount)
        val stateGuard = buildGuardExpr(g, e0)
        if (!stateGuard.empty) parts.add(stateGuard)
        parts.join(" && ")
    }

    def private String buildGuardExpr(Guard g, String e0) {
        switch g {
            StateEquality:    if (g.entity == e0) "" else stateVar(g.entity) + " == " + stateConst(g.entity, g.state)
            StateInequality:  stateVar(g.entity) + " != " + stateConst(g.entity, g.state)
            PropComparison:   propVar(g.ref.entity, g.ref.prop) + " " + compOpSymbol(g.op) + " " + buildValueExpr(g.value)
            BetweenGuard:     propVar(g.ref.entity, g.ref.prop) + " >= " + buildValueExpr(g.low) + " && " + propVar(g.ref.entity, g.ref.prop) + " <= " + buildValueExpr(g.high)
            ConjunctionGuard: {
                val l = buildGuardExpr(g.left, e0)
                val r = buildGuardExpr(g.right, e0)
                if (l.empty) r else if (r.empty) l else l + " && " + r
            }
        }
    }

    def private String buildUpdate(Effect e, String entityName, String allClockResets) {
        val parts = collectUpdateParts(e, entityName)
        if (!allClockResets.empty) parts.add(allClockResets)
        parts.join(", ")
    }

    def private List<String> collectUpdateParts(Effect e, String entityName) {
        val parts = new ArrayList<String>
        switch e {
            StateAssign: {
                if (e.entity == entityName)
                    parts.add(stateVar(e.entity) + " = " + stateConst(e.entity, e.state))
            }
            PropAssign:  parts.add(propVar(e.ref.entity, e.ref.prop) + " = " + buildValueExpr(e.value))
            PropDelta: {
                val op = if (e.op == AbstractModel.DeltaOp.INC) "+=" else "-="
                parts.add(propVar(e.ref.entity, e.ref.prop) + " " + op + " " + buildValueExpr(e.value))
            }
            PropRandom:     parts.add(propVar(e.ref.entity, e.ref.prop) + " = i")
            SequenceEffect: {
                parts.addAll(collectUpdateParts(e.left, entityName))
                parts.addAll(collectUpdateParts(e.right, entityName))
            }
        }
        parts
    }

    def private String buildSelect(Effect e) {
        val random = findPropRandom(e)
        if (random === null) return null
        "i : int[" + buildValueExpr(random.low) + ", " + buildValueExpr(random.high) + "]"
    }

    def private PropRandom findPropRandom(Effect e) {
        switch e {
            PropRandom:     e
            SequenceEffect: {
                val l = findPropRandom(e.left)
                if (l !== null) l else findPropRandom(e.right)
            }
            default: null
        }
    }

    def private List<StateAssign> collectStateAssigns(Effect e) {
        val result = new ArrayList<StateAssign>
        switch e {
            StateAssign:    result.add(e)
            SequenceEffect: {
                result.addAll(collectStateAssigns(e.left))
                result.addAll(collectStateAssigns(e.right))
            }
        }
        result
    }

    def private String getSourceState(Guard g, String entityName) {
        switch g {
            StateEquality:    if (g.entity == entityName) g.state else null
            ConjunctionGuard: {
                val l = getSourceState(g.left, entityName)
                if (l !== null) l else getSourceState(g.right, entityName)
            }
            default: null
        }
    }

    def private boolean isUrgentLocation(String entityName, String state, List<Scenario> scenarios) {
        scenarios.exists[s |
            s.event.clock !== null && s.event.clock.amount == 0 && getSourceState(s.guard, entityName) == state
        ]
    }

    def private String getLocationInvariant(String entityName, String state, List<Scenario> scenarios) {
        val amounts = scenarios
            .filter[s | s.event.clock !== null && s.event.clock.amount > 0 && getSourceState(s.guard, entityName) == state]
            .map[s | s.event.clock.amount]
            .toSet
        if (amounts.empty) return null
        amounts.map[v | clockName(v) + " <= " + v].join(" && ")
    }

    def private String ownerOf(Scenario sc) {
        ownerOfGuard(sc.guard)
    }

    def private String ownerOfGuard(Guard g) {
        switch g {
            StateEquality:    g.entity
            ConjunctionGuard: ownerOfGuard(g.left) ?: ownerOfGuard(g.right)
            default: null
        }
    }

    def private boolean hasOnEnd(Scenario sc) {
        sc.event.onEnd !== null
    }

    def private String onEndEntity(Scenario sc) {
        sc.event.onEnd.entity
    }

    def private String onEndState(Scenario sc) {
        sc.event.onEnd.state
    }

    def private String buildValueExpr(AbstractModel.Value v) {
        switch v {
            NumberValue: String.valueOf(v.value)
            PropValue:   propVar(v.ref.entity, v.ref.prop)
        }
    }

    def private String compOpSymbol(AbstractModel.CompOp op) {
        switch op {
            case EQ:  "=="
            case NEQ: "!="
            case GT:  ">"
            case LT:  "<"
            case GEQ: ">="
            case LEQ: "<="
        }
    }

    def private String clockName(int amount) {
        "c_" + amount
    }

    def private String locKey(String entity, String state) {
        entity + "." + state
    }

    def private String finishingLocKey(String entity, Scenario sc) {
        entity + "." + onEndState(sc) + "_finishing_" + sc.name
    }

    def private String stateConst(String entity, String state) {
        (entity + "_" + state).toUpperCase
    }

    def private String stateVar(String entity) {
        entity + "_state"
    }

    def private String propVar(String entity, String prop) {
        entity + "_" + prop
    }

    def private String nextId() {
        "id" + idCounter++
    }

    def private String generateQueries(Model m) {
        m.requirements.map[generateQuery].join("\n")
    }

    def private String generateQuery(AbstractModel.Requirement r) {
        val formula = generateFormula(r)
        '''		<query>
			<formula>«xmlEscape(formula)»</formula>
			<comment>«r.name»</comment>
		</query>'''
    }

    def private String generateFormula(AbstractModel.Requirement r) {
        val tb = r.timeBound
        switch r.check {
            AbstractModel.CheckQuery: {
                val c  = r.check as AbstractModel.CheckQuery
                val sf = buildGuardExpr(c.cond, "")
                val op = if (c.diamond) "<>" else "[]"
                if (c.existential) {
                    if (tb !== null)
                        "Pr[<=" + tb.amount + "] (" + op + " " + sf + ")"
                    else
                        "E" + op + " (" + sf + ")"
                } else {
                    "A" + op + " (" + sf + ")"
                }
            }
            AbstractModel.NeverCheck: {
                val c = r.check as AbstractModel.NeverCheck
                "A[] not (" + buildGuardExpr(c.cond, "") + ")"
            }
            AbstractModel.MaxCheck: {
                val c = r.check as AbstractModel.MaxCheck
                "E[<=" + tb.amount + "; 100] (max: " + propVar(c.ref.entity, c.ref.prop) + ")"
            }
            AbstractModel.MinCheck: {
                val c = r.check as AbstractModel.MinCheck
                "E[<=" + tb.amount + "; 100] (min: " + propVar(c.ref.entity, c.ref.prop) + ")"
            }
        }
    }

    def private String xmlEscape(String s) {
        s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    }
}