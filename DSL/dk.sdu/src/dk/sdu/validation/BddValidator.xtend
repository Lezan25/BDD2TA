package dk.sdu.validation

import dk.sdu.bdd.BddPackage
import dk.sdu.bdd.Entity
import dk.sdu.bdd.Model
import dk.sdu.bdd.Scenario
import dk.sdu.bdd.StateCondition
import dk.sdu.bdd.StateEffect
import dk.sdu.bdd.TimeUnit
import dk.sdu.bdd.WhenTrigger
import dk.sdu.bdd.WheneverTrigger
import org.eclipse.xtext.validation.Check

class BddValidator extends AbstractBddValidator {

    static val DUPLICATE_ENTITY       = "dk.sdu.bdd.DuplicateEntity"
    static val DUPLICATE_SCENARIO     = "dk.sdu.bdd.DuplicateScenario"
    static val DUPLICATE_STATE        = "dk.sdu.bdd.DuplicateState"
    static val DUPLICATE_PROPERTY     = "dk.sdu.bdd.DuplicateProperty"
    static val NO_STATES              = "dk.sdu.bdd.NoStates"
    static val FIRST_GIVEN_NOT_STATE  = "dk.sdu.bdd.FirstGivenNotState"
    static val DUPLICATE_ENTITY_GUARD = "dk.sdu.bdd.DuplicateEntityGuard"
    static val MIXED_TIME_UNITS       = "dk.sdu.bdd.MixedTimeUnits"
    static val ON_END_MISSING_GIVEN   = "dk.sdu.bdd.OnEndMissingGiven"
    static val ON_END_TRIVIAL         = "dk.sdu.bdd.OnEndTrivial"
    static val DUPLICATE_STATE_EFFECT = "dk.sdu.bdd.DuplicateStateEffect"
    static val DUPLICATE_PROP_EFFECT  = "dk.sdu.bdd.DuplicatePropEffect"

    @Check
    def checkDuplicateEntityNames(Model m) {
        val seen = newHashSet
        for (e : m.entities)
            if (!seen.add(e.name))
                error("Duplicate entity name '" + e.name + "'",
                    e, BddPackage.Literals.ENTITY__NAME, DUPLICATE_ENTITY)
    }

    @Check
    def checkDuplicateScenarioNames(Model m) {
        val seen = newHashSet
        for (s : m.scenarios)
            if (!seen.add(s.name))
                error("Duplicate scenario name '" + s.name + "'",
                    s, BddPackage.Literals.SCENARIO__NAME, DUPLICATE_SCENARIO)
    }

    @Check
    def checkEntityHasAtLeastOneState(Entity e) {
        if (e.states.empty)
            error("Entity '" + e.name + "' must declare at least one state",
                e, BddPackage.Literals.ENTITY__NAME, NO_STATES)
    }

    @Check
    def checkDuplicateStateNames(Entity e) {
        val seen = newHashSet
        for (s : e.states)
            if (!seen.add(s.name))
                error("Duplicate state name '" + s.name + "' in entity '" + e.name + "'",
                    s, BddPackage.Literals.STATE__NAME, DUPLICATE_STATE)
    }

    @Check
    def checkDuplicatePropertyNames(Entity e) {
        val seen = newHashSet
        for (p : e.properties)
            if (!seen.add(p.name))
                error("Duplicate property name '" + p.name + "' in entity '" + e.name + "'",
                    p, BddPackage.Literals.PROPERTY__NAME, DUPLICATE_PROPERTY)
    }

    @Check
    def checkFirstGivenIsStateCondition(Scenario s) {
        if (!s.guards.empty && !(s.guards.head instanceof StateCondition))
            error("The first 'given' condition must assert an entity state (e.g. 'robot is in idle')",
                s, BddPackage.Literals.SCENARIO__GUARDS, FIRST_GIVEN_NOT_STATE)
    }

    @Check
    def checkNoDuplicateEntityStateConditions(Scenario s) {
        val seen = newHashSet
        for (c : s.guards.filter(StateCondition))
            if (!seen.add(c.entity?.name))
                error("Entity '" + c.entity?.name + "' appears in more than one state condition in the same scenario",
                    c, BddPackage.Literals.STATE_CONDITION__ENTITY, DUPLICATE_ENTITY_GUARD)
    }

    @Check
    def checkConsistentTimeUnits(Model m) {
        val timeEvents = m.scenarios
            .map[s | s.trigger]
            .filter[t | t !== null]
            .map[t |
                switch t {
                    WhenTrigger:     t.event
                    WheneverTrigger: t.event
                    default: null
                }
            ]
            .filter[e | e !== null && e.eIsSet(e.eClass.getEStructuralFeature("time"))]
            .map[e | e.time]
            .toList

        if (timeEvents.size < 2) return

        val firstUnit = timeEvents.head.unit
        for (te : timeEvents.tail)
            if (te.unit != firstUnit)
                error("All scenarios must use the same time unit. Expected '" +
                    unitLabel(firstUnit) + "' but found '" + unitLabel(te.unit) +
                    "'. Consider changing this to '" + unitLabel(firstUnit) + "'",
                    te, BddPackage.Literals.TIME_EVENT__UNIT, MIXED_TIME_UNITS)
    }

    @Check
    def checkOnEndHasMatchingGiven(Scenario s) {
        val endEvent = getEndEvent(s)
        if (endEvent === null) return

        val matchingGiven = s.guards.filter(StateCondition).findFirst[c |
            c.entity == endEvent.entity && c.state == endEvent.state
        ]
        if (matchingGiven === null)
            error("When using 'finishes', the given clause must assert that '" +
                endEvent.entity?.name + "' is in '" + endEvent.state?.name +
                "'. Add: 'and " + endEvent.entity?.name + " is in " + endEvent.state?.name + "'",
                endEvent, BddPackage.Literals.END_EVENT__ENTITY, ON_END_MISSING_GIVEN)
    }

    @Check
    def checkOnEndIsNotTrivial(Scenario s) {
        val endEvent = getEndEvent(s)
        if (endEvent === null) return

        val firstStateCond = s.guards.filter(StateCondition).head
        if (firstStateCond !== null &&
            endEvent.entity == firstStateCond.entity &&
            endEvent.state == firstStateCond.state)
            error("The 'finishes' event cannot reference the same entity and state as the first given condition",
                endEvent, BddPackage.Literals.END_EVENT__ENTITY, ON_END_TRIVIAL)
    }

    @Check
    def checkNoDuplicateStateEffects(Scenario s) {
        val seen = newHashSet
        for (e : s.effects.filter(StateEffect))
            if (!seen.add(e.entity?.name))
                error("State of entity '" + e.entity?.name + "' is assigned more than once in the same scenario",
                    e, BddPackage.Literals.STATE_EFFECT__ENTITY, DUPLICATE_STATE_EFFECT)
    }

    @Check
    def checkNoDuplicatePropEffects(Scenario s) {
        val seen = newHashSet
        for (e : s.effects.filter(dk.sdu.bdd.AssignEffect))
            if (!seen.add(propKey(e.ref)))
                error("Property '" + propKey(e.ref) + "' is assigned more than once in the same scenario",
                    e, BddPackage.Literals.ASSIGN_EFFECT__REF, DUPLICATE_PROP_EFFECT)
        for (e : s.effects.filter(dk.sdu.bdd.RandomEffect))
            if (!seen.add(propKey(e.ref)))
                error("Property '" + propKey(e.ref) + "' is assigned more than once in the same scenario",
                    e, BddPackage.Literals.RANDOM_EFFECT__REF, DUPLICATE_PROP_EFFECT)
    }

    @Check
    def checkTimeBoundNotAllowed(dk.sdu.bdd.Requirement r) {
        val check = r.check
        val hasBound = r.timeBound !== null
        if (check instanceof dk.sdu.bdd.NeverCheck && hasBound)
            error("'never' checks must not have a time bound",
                r, BddPackage.Literals.REQUIREMENT__TIME_BOUND, "dk.sdu.bdd.TimeBoundNotAllowed")
        if (check instanceof dk.sdu.bdd.CheckQuery && hasBound && !(check as dk.sdu.bdd.CheckQuery).isExistential)
            error("Universally-quantified checks must not have a time bound",
                r, BddPackage.Literals.REQUIREMENT__TIME_BOUND, "dk.sdu.bdd.TimeBoundNotAllowed")
    }

    @Check
    def checkTimeBoundRequired(dk.sdu.bdd.Requirement r) {
        val check = r.check
        if ((check instanceof dk.sdu.bdd.MaxCheck || check instanceof dk.sdu.bdd.MinCheck) && r.timeBound === null)
            error("'maximum' and 'minimum' checks require a 'within' time bound",
                r, BddPackage.Literals.REQUIREMENT__CHECK, "dk.sdu.bdd.TimeBoundRequired")
    }

    def private dk.sdu.bdd.EndEvent getEndEvent(Scenario s) {
        val trigger = s.trigger
        val concreteEvent = switch trigger {
            WhenTrigger:     trigger.event
            WheneverTrigger: trigger.event
            default: null
        }
        if (concreteEvent === null) return null
        if (!concreteEvent.eIsSet(concreteEvent.eClass.getEStructuralFeature("end"))) return null
        concreteEvent.end
    }

    def private String propKey(dk.sdu.bdd.PropRef ref) {
        ref?.entity?.name + "." + ref?.prop?.name
    }

    def private String unitLabel(TimeUnit u) {
        switch u {
            case MILLISECONDS: "milliseconds"
            case SECONDS:      "seconds"
            case MINUTES:      "minutes"
            case HOURS:        "hours"
        }
    }
}