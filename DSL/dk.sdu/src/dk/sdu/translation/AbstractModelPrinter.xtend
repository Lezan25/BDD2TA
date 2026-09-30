package dk.sdu.translation

import dk.sdu.model.AbstractModel
import dk.sdu.model.AbstractModel.BetweenGuard
import dk.sdu.model.AbstractModel.ConjunctionGuard
import dk.sdu.model.AbstractModel.Effect
import dk.sdu.model.AbstractModel.Entity
import dk.sdu.model.AbstractModel.Event
import dk.sdu.model.AbstractModel.Guard
import dk.sdu.model.AbstractModel.NumberValue
import dk.sdu.model.AbstractModel.NumericProperty
import dk.sdu.model.AbstractModel.PropAssign
import dk.sdu.model.AbstractModel.PropComparison
import dk.sdu.model.AbstractModel.PropDelta
import dk.sdu.model.AbstractModel.PropRandom
import dk.sdu.model.AbstractModel.PropRef
import dk.sdu.model.AbstractModel.PropValue
import dk.sdu.model.AbstractModel.Property
import dk.sdu.model.AbstractModel.Scenario
import dk.sdu.model.AbstractModel.SequenceEffect
import dk.sdu.model.AbstractModel.StateAssign
import dk.sdu.model.AbstractModel.StateEquality
import dk.sdu.model.AbstractModel.StateInequality
import dk.sdu.model.AbstractModel.StateProperty
import dk.sdu.model.AbstractModel.StatesProperty
import dk.sdu.model.AbstractModel.Value

class AbstractModelPrinter {

    def String print(AbstractModel.Model m) {
        val entities     = m.entities.map[printEntity].join(",\n" + indent(1))
        val scenarios    = m.scenarios.map[printScenario].join(",\n" + indent(1))
        val requirements = m.requirements.map[printRequirement].join(",\n" + indent(1))
        val reqPart      = if (m.requirements.empty) "" else ",\n" + indent(1) + requirements
        '''model(«m.name»,
«indent(1)»«entities»,
«indent(1)»«scenarios»«reqPart»)'''
    }

    def private String printEntity(Entity e) {
        val props = e.properties.map[printProperty].join(",\n" + indent(2))
        '''entity(«e.name»,
«indent(2)»«props»)'''
    }

    def private String printProperty(Property p) {
        switch p {
            StatesProperty:  "states = [" + p.stateNames.join(", ") + "]"
            StateProperty:   "state = " + p.stateName
            NumericProperty: p.name + " = " + p.value
        }
    }

    def private String printScenario(Scenario s) {
        '''scenario(«s.name»,
«indent(2)»«printGuard(s.guard)»,
«indent(2)»«printEvent(s.event)»,
«indent(2)»«printEffect(s.effect)»)'''
    }

    def private String printGuard(Guard g) {
        switch g {
            StateEquality:    g.entity + ".state = " + g.state
            StateInequality:  g.entity + ".state \u2260 " + g.state
            PropComparison:   printPropRef(g.ref) + " " + printCompOp(g.op) + " " + printValue(g.value)
            BetweenGuard:     printPropRef(g.ref) + " \u2265 " + printValue(g.low) + " \u2227 " + printPropRef(g.ref) + " \u2264 " + printValue(g.high)
            ConjunctionGuard: printGuard(g.left) + " \u2227 " + printGuard(g.right)
        }
    }

    def private String printEvent(Event ev) {
        val clock     = ev.clock
        val onEnd     = ev.onEnd
        val clockPart = if (clock !== null) clock.entity + ".clock = " + clock.amount else null
        val endPart   = if (onEnd !== null) "on_end(" + onEnd.entity + ", " + onEnd.state + ")" else null
        if (clockPart !== null && endPart !== null) clockPart + " & " + endPart
        else if (clockPart !== null) clockPart
        else endPart
    }

    def private String printEffect(Effect e) {
        switch e {
            StateAssign:    e.entity + ".state = " + e.state
            PropAssign:     printPropRef(e.ref) + " = " + printValue(e.value)
            PropDelta: {
                val op = if (e.op == AbstractModel.DeltaOp.INC) " + " else " - "
                printPropRef(e.ref) + " = " + printPropRef(e.ref) + op + printValue(e.value)
            }
            PropRandom:     printPropRef(e.ref) + " = random(" + printValue(e.low) + ", " + printValue(e.high) + ")"
            SequenceEffect: printEffect(e.left) + " ;\n" + indent(2) + printEffect(e.right)
        }
    }

    def private String printPropRef(PropRef r) {
        r.entity + "." + r.prop
    }

    def private String printValue(Value v) {
        switch v {
            NumberValue: String.valueOf(v.value)
            PropValue:   printPropRef(v.ref)
        }
    }

    def private String printCompOp(AbstractModel.CompOp op) {
        switch (op) {
            case EQ:  "="
            case NEQ: "\u2260"
            case GT:  ">"
            case LT:  "<"
            case GEQ: "\u2265"
            case LEQ: "\u2264"
        }
    }

    def private String indent(int level) {
        "  ".repeat(level)
    }

    def private String printRequirement(AbstractModel.Requirement r) {
        val tb = if (r.timeBound !== null) ",\n" + indent(2) + "within(" + r.timeBound.amount + ")" else ""
        '''requirement(«r.name»«tb»,
«indent(2)»«printCheck(r.check)»)'''
    }

    def private String printCheck(AbstractModel.Check c) {
        switch c {
            AbstractModel.CheckQuery: {
                val q = if (c.existential) "E" else "A"
                val m = if (c.diamond) "<>" else "[]"
                "check(" + q + ", " + m + ", " + printGuard(c.cond) + ")"
            }
            AbstractModel.NeverCheck: "never(" + printGuard(c.cond) + ")"
            AbstractModel.MaxCheck:   "max(" + printPropRef(c.ref) + ")"
            AbstractModel.MinCheck:   "min(" + printPropRef(c.ref) + ")"
        }
    }
}