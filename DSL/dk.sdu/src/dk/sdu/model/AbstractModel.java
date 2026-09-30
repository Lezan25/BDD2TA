package dk.sdu.model;

import java.util.List;

public class AbstractModel {

    public record Model(
        String name,
        List<Entity> entities,
        List<Scenario> scenarios,
        List<Requirement> requirements
    ) {}

    public record Entity(
        String name,
        List<Property> properties
    ) {}

    public sealed interface Property permits StatesProperty, StateProperty, NumericProperty {}
    public record StatesProperty(List<String> stateNames) implements Property {}
    public record StateProperty(String stateName) implements Property {}
    public record NumericProperty(String name, int value) implements Property {}

    public record Scenario(
        String name,
        Guard guard,
        Event event,
        Effect effect
    ) {}

    public sealed interface Guard permits StateEquality, StateInequality, PropComparison, BetweenGuard, ConjunctionGuard {}
    public record StateEquality(String entity, String state) implements Guard {}
    public record StateInequality(String entity, String state) implements Guard {}
    public record PropComparison(PropRef ref, CompOp op, Value value) implements Guard {}
    public record BetweenGuard(PropRef ref, Value low, Value high) implements Guard {}
    public record ConjunctionGuard(Guard left, Guard right) implements Guard {}

    public record Event(ClockEvent clock, OnEnd onEnd) {}
    public record ClockEvent(String entity, int amount) {}
    public record OnEnd(String entity, String state) {}

    public sealed interface Effect permits StateAssign, PropAssign, PropDelta, PropRandom, SequenceEffect {}
    public record StateAssign(String entity, String state) implements Effect {}
    public record PropAssign(PropRef ref, Value value) implements Effect {}
    public record PropDelta(PropRef ref, DeltaOp op, Value value) implements Effect {}
    public record PropRandom(PropRef ref, Value low, Value high) implements Effect {}
    public record SequenceEffect(Effect left, Effect right) implements Effect {}

    public record PropRef(String entity, String prop) {}

    public sealed interface Value permits PropValue, NumberValue {}
    public record PropValue(PropRef ref) implements Value {}
    public record NumberValue(int value) implements Value {}

    public enum CompOp { EQ, NEQ, GT, LT, GEQ, LEQ }
    public enum DeltaOp { INC, DEC }

    // --- Requirements ---

    public record Requirement(
        String name,
        TimeBound timeBound,
        Check check
    ) {}

    public record TimeBound(int amount) {}

    public sealed interface Check permits CheckQuery, NeverCheck, MaxCheck, MinCheck {}
    public record CheckQuery(boolean existential, boolean diamond, Guard cond) implements Check {}
    public record NeverCheck(Guard cond) implements Check {}
    public record MaxCheck(PropRef ref) implements Check {}
    public record MinCheck(PropRef ref) implements Check {}
}