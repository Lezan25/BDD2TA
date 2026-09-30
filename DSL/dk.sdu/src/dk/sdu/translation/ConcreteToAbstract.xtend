package dk.sdu.translation

import dk.sdu.bdd.BetweenCondition
import dk.sdu.bdd.DeltaEffect
import dk.sdu.bdd.DeltaOp
import dk.sdu.bdd.EndEvent
import dk.sdu.bdd.Entity
import dk.sdu.bdd.Model
import dk.sdu.bdd.NumberValue
import dk.sdu.bdd.PropCondition
import dk.sdu.bdd.PropRef
import dk.sdu.bdd.PropValue
import dk.sdu.bdd.Property
import dk.sdu.bdd.RandomEffect
import dk.sdu.bdd.AssignEffect
import dk.sdu.bdd.Scenario
import dk.sdu.bdd.StateCondition
import dk.sdu.bdd.StateEffect
import dk.sdu.bdd.Value
import dk.sdu.bdd.WheneverTrigger
import dk.sdu.model.AbstractModel
import dk.sdu.model.AbstractModel.BetweenGuard
import dk.sdu.model.AbstractModel.ClockEvent
import dk.sdu.model.AbstractModel.ConjunctionGuard
import dk.sdu.model.AbstractModel.Effect
import dk.sdu.model.AbstractModel.Event
import dk.sdu.model.AbstractModel.Guard
import dk.sdu.model.AbstractModel.NumericProperty
import dk.sdu.model.AbstractModel.OnEnd
import dk.sdu.model.AbstractModel.PropAssign
import dk.sdu.model.AbstractModel.PropComparison
import dk.sdu.model.AbstractModel.PropDelta
import dk.sdu.model.AbstractModel.PropRandom
import dk.sdu.model.AbstractModel.SequenceEffect
import dk.sdu.model.AbstractModel.StateAssign
import dk.sdu.model.AbstractModel.StateEquality
import dk.sdu.model.AbstractModel.StateInequality
import dk.sdu.model.AbstractModel.StateProperty
import dk.sdu.model.AbstractModel.StatesProperty
import java.util.List
import dk.sdu.bdd.WhenTrigger

class ConcreteToAbstract {

    def AbstractModel.Model translateModel(Model m) {
        new AbstractModel.Model(
            m.name,
            m.entities.map[translateEntity],
            m.scenarios.map[translateScenario],
            m.getRequirements.map[translateRequirement]
        )
    }

    def AbstractModel.Entity translateEntity(Entity e) {
        val stateNames = e.states.map[name]
        val List<AbstractModel.Property> propsHead = List.of(
            new StatesProperty(stateNames),
            new StateProperty(stateNames.get(0))
        )
        val List<AbstractModel.Property> propsTail = e.properties.map[translateProperty as AbstractModel.Property].toList
        new AbstractModel.Entity(e.name, (propsHead + propsTail).toList)
    }

    def NumericProperty translateProperty(Property p) {
        new NumericProperty(p.name, p.value)
    }

    def AbstractModel.Scenario translateScenario(Scenario s) {
        val firstStateCond = s.guards.filter(StateCondition).head
        val e0 = firstStateCond.entity.name
        val s0 = firstStateCond.state.name

        val guard  = foldGuards(s.guards.map[translateCondition].toList)
        val event  = translateEvent(s, e0)
        val rawFx  = foldEffects(s.effects.map[translateEffect].toList)

        val effect = if (s.trigger instanceof WheneverTrigger)
            new SequenceEffect(new StateAssign(e0, s0), rawFx)
        else
            rawFx

        new AbstractModel.Scenario(s.name, guard, event, effect)
    }

    def Guard foldGuards(List<Guard> guards) {
        guards.reduce[acc, g | new ConjunctionGuard(acc, g)]
    }

    def Effect foldEffects(List<Effect> effects) {
        effects.reduce[acc, f | new SequenceEffect(acc, f)]
    }

    def dispatch Guard translateCondition(StateCondition c) {
        if (c.isNegated)
            new StateInequality(c.entity.name, c.state.name)
        else
            new StateEquality(c.entity.name, c.state.name)
    }

    def dispatch Guard translateCondition(PropCondition c) {
        new PropComparison(
            translatePropRef(c.ref),
            translateComp(c.comp),
            translateValue(c.value)
        )
    }

    def dispatch Guard translateCondition(BetweenCondition c) {
        new BetweenGuard(
            translatePropRef(c.ref),
            translateValue(c.low),
            translateValue(c.high)
        )
    }

    def Event translateEvent(Scenario s, String e0) {
        val concreteEvent = switch s.trigger {
            WhenTrigger:     s.trigger.event
            WheneverTrigger: s.trigger.event
        }

        val hasTime = concreteEvent.eIsSet(concreteEvent.eClass.getEStructuralFeature("time"))
        val hasEnd  = concreteEvent.eIsSet(concreteEvent.eClass.getEStructuralFeature("end"))

        val clock = if (hasTime) new ClockEvent(e0, concreteEvent.time.amount) else null
        val onEnd = if (hasEnd) translateEndEvent(concreteEvent.end) else null

        new Event(clock, onEnd)
    }

    def OnEnd translateEndEvent(EndEvent e) {
        new OnEnd(e.entity.name, e.state.name)
    }

    def dispatch Effect translateEffect(StateEffect e) {
        new StateAssign(e.entity.name, e.state.name)
    }

    def dispatch Effect translateEffect(AssignEffect e) {
        new PropAssign(translatePropRef(e.ref), translateValue(e.value))
    }

    def dispatch Effect translateEffect(DeltaEffect e) {
        new PropDelta(
            translatePropRef(e.ref),
            if (e.op == DeltaOp.INC) AbstractModel.DeltaOp.INC else AbstractModel.DeltaOp.DEC,
            translateValue(e.value)
        )
    }

    def dispatch Effect translateEffect(RandomEffect e) {
        new PropRandom(
            translatePropRef(e.ref),
            translateValue(e.low),
            translateValue(e.high)
        )
    }

    def AbstractModel.PropRef translatePropRef(PropRef r) {
        new AbstractModel.PropRef(r.entity.name, r.prop.name)
    }

    def dispatch AbstractModel.Value translateValue(NumberValue v) {
        new AbstractModel.NumberValue(v.value)
    }

    def dispatch AbstractModel.Value translateValue(PropValue v) {
        new AbstractModel.PropValue(translatePropRef(v.ref))
    }

    def AbstractModel.CompOp translateComp(dk.sdu.bdd.Comp c) {
        switch (c) {
            case EQ:  AbstractModel.CompOp.EQ
            case NEQ: AbstractModel.CompOp.NEQ
            case GT:  AbstractModel.CompOp.GT
            case LT:  AbstractModel.CompOp.LT
            case GEQ: AbstractModel.CompOp.GEQ
            case LEQ: AbstractModel.CompOp.LEQ
        }
    }

    def AbstractModel.Requirement translateRequirement(dk.sdu.bdd.Requirement r) {
        val timeBound = if (r.timeBound !== null) new AbstractModel.TimeBound(r.timeBound.amount) else null
        val check     = translateCheck(r.check)
        new AbstractModel.Requirement(r.name, timeBound, check)
    }

    def AbstractModel.Check translateCheck(dk.sdu.bdd.Check c) {
        switch c {
            dk.sdu.bdd.CheckQuery: new AbstractModel.CheckQuery(
                c.isExistential,
                c.isDiamond,
                translateReqCond(c.eGet(c.eClass.getEStructuralFeature("cond")))
            )
            dk.sdu.bdd.NeverCheck: new AbstractModel.NeverCheck(
                translateReqCond(c.eGet(c.eClass.getEStructuralFeature("cond")))
            )
            dk.sdu.bdd.MaxCheck: new AbstractModel.MaxCheck(translatePropRef(c.ref))
            dk.sdu.bdd.MinCheck: new AbstractModel.MinCheck(translatePropRef(c.ref))
        }
    }

    def AbstractModel.Guard translateReqCond(Object cond) {
        if (cond instanceof dk.sdu.bdd.StateCondition)
            return translateCondition(cond as dk.sdu.bdd.StateCondition)
        if (cond instanceof dk.sdu.bdd.PropCondition)
            return translateCondition(cond as dk.sdu.bdd.PropCondition)
        if (cond instanceof dk.sdu.bdd.BetweenCondition)
            return translateCondition(cond as dk.sdu.bdd.BetweenCondition)
        throw new IllegalArgumentException("Unknown condition type: " + cond?.class)
    }
}