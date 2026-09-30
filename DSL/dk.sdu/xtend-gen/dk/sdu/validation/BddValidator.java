package dk.sdu.validation;

import com.google.common.collect.Iterables;
import dk.sdu.bdd.AssignEffect;
import dk.sdu.bdd.BddPackage;
import dk.sdu.bdd.CheckQuery;
import dk.sdu.bdd.Condition;
import dk.sdu.bdd.EndEvent;
import dk.sdu.bdd.Entity;
import dk.sdu.bdd.Event;
import dk.sdu.bdd.MaxCheck;
import dk.sdu.bdd.MinCheck;
import dk.sdu.bdd.Model;
import dk.sdu.bdd.NeverCheck;
import dk.sdu.bdd.PropRef;
import dk.sdu.bdd.Property;
import dk.sdu.bdd.RandomEffect;
import dk.sdu.bdd.Requirement;
import dk.sdu.bdd.Scenario;
import dk.sdu.bdd.StateCondition;
import dk.sdu.bdd.StateEffect;
import dk.sdu.bdd.TimeBound;
import dk.sdu.bdd.TimeEvent;
import dk.sdu.bdd.TimeUnit;
import dk.sdu.bdd.Trigger;
import dk.sdu.bdd.WhenTrigger;
import dk.sdu.bdd.WheneverTrigger;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class BddValidator extends AbstractBddValidator {
  private static final String DUPLICATE_ENTITY = "dk.sdu.bdd.DuplicateEntity";

  private static final String DUPLICATE_SCENARIO = "dk.sdu.bdd.DuplicateScenario";

  private static final String DUPLICATE_STATE = "dk.sdu.bdd.DuplicateState";

  private static final String DUPLICATE_PROPERTY = "dk.sdu.bdd.DuplicateProperty";

  private static final String NO_STATES = "dk.sdu.bdd.NoStates";

  private static final String FIRST_GIVEN_NOT_STATE = "dk.sdu.bdd.FirstGivenNotState";

  private static final String DUPLICATE_ENTITY_GUARD = "dk.sdu.bdd.DuplicateEntityGuard";

  private static final String MIXED_TIME_UNITS = "dk.sdu.bdd.MixedTimeUnits";

  private static final String ON_END_MISSING_GIVEN = "dk.sdu.bdd.OnEndMissingGiven";

  private static final String ON_END_TRIVIAL = "dk.sdu.bdd.OnEndTrivial";

  private static final String DUPLICATE_STATE_EFFECT = "dk.sdu.bdd.DuplicateStateEffect";

  private static final String DUPLICATE_PROP_EFFECT = "dk.sdu.bdd.DuplicatePropEffect";

  @Check
  public void checkDuplicateEntityNames(final Model m) {
    final HashSet<String> seen = CollectionLiterals.<String>newHashSet();
    EList<Entity> _entities = m.getEntities();
    for (final Entity e : _entities) {
      boolean _add = seen.add(e.getName());
      boolean _not = (!_add);
      if (_not) {
        String _name = e.getName();
        String _plus = ("Duplicate entity name \'" + _name);
        String _plus_1 = (_plus + "\'");
        this.error(_plus_1, e, BddPackage.Literals.ENTITY__NAME, BddValidator.DUPLICATE_ENTITY);
      }
    }
  }

  @Check
  public void checkDuplicateScenarioNames(final Model m) {
    final HashSet<String> seen = CollectionLiterals.<String>newHashSet();
    EList<Scenario> _scenarios = m.getScenarios();
    for (final Scenario s : _scenarios) {
      boolean _add = seen.add(s.getName());
      boolean _not = (!_add);
      if (_not) {
        String _name = s.getName();
        String _plus = ("Duplicate scenario name \'" + _name);
        String _plus_1 = (_plus + "\'");
        this.error(_plus_1, s, BddPackage.Literals.SCENARIO__NAME, BddValidator.DUPLICATE_SCENARIO);
      }
    }
  }

  @Check
  public void checkEntityHasAtLeastOneState(final Entity e) {
    boolean _isEmpty = e.getStates().isEmpty();
    if (_isEmpty) {
      String _name = e.getName();
      String _plus = ("Entity \'" + _name);
      String _plus_1 = (_plus + "\' must declare at least one state");
      this.error(_plus_1, e, BddPackage.Literals.ENTITY__NAME, BddValidator.NO_STATES);
    }
  }

  @Check
  public void checkDuplicateStateNames(final Entity e) {
    final HashSet<String> seen = CollectionLiterals.<String>newHashSet();
    EList<dk.sdu.bdd.State> _states = e.getStates();
    for (final dk.sdu.bdd.State s : _states) {
      boolean _add = seen.add(s.getName());
      boolean _not = (!_add);
      if (_not) {
        String _name = s.getName();
        String _plus = ("Duplicate state name \'" + _name);
        String _plus_1 = (_plus + "\' in entity \'");
        String _name_1 = e.getName();
        String _plus_2 = (_plus_1 + _name_1);
        String _plus_3 = (_plus_2 + "\'");
        this.error(_plus_3, s, BddPackage.Literals.STATE__NAME, BddValidator.DUPLICATE_STATE);
      }
    }
  }

  @Check
  public void checkDuplicatePropertyNames(final Entity e) {
    final HashSet<String> seen = CollectionLiterals.<String>newHashSet();
    EList<Property> _properties = e.getProperties();
    for (final Property p : _properties) {
      boolean _add = seen.add(p.getName());
      boolean _not = (!_add);
      if (_not) {
        String _name = p.getName();
        String _plus = ("Duplicate property name \'" + _name);
        String _plus_1 = (_plus + "\' in entity \'");
        String _name_1 = e.getName();
        String _plus_2 = (_plus_1 + _name_1);
        String _plus_3 = (_plus_2 + "\'");
        this.error(_plus_3, p, BddPackage.Literals.PROPERTY__NAME, BddValidator.DUPLICATE_PROPERTY);
      }
    }
  }

  @Check
  public void checkFirstGivenIsStateCondition(final Scenario s) {
    if (((!s.getGuards().isEmpty()) && (!(IterableExtensions.<Condition>head(s.getGuards()) instanceof StateCondition)))) {
      this.error("The first \'given\' condition must assert an entity state (e.g. \'robot is in idle\')", s, BddPackage.Literals.SCENARIO__GUARDS, BddValidator.FIRST_GIVEN_NOT_STATE);
    }
  }

  @Check
  public void checkNoDuplicateEntityStateConditions(final Scenario s) {
    final HashSet<String> seen = CollectionLiterals.<String>newHashSet();
    Iterable<StateCondition> _filter = Iterables.<StateCondition>filter(s.getGuards(), StateCondition.class);
    for (final StateCondition c : _filter) {
      Entity _entity = c.getEntity();
      String _name = null;
      if (_entity!=null) {
        _name=_entity.getName();
      }
      boolean _add = seen.add(_name);
      boolean _not = (!_add);
      if (_not) {
        Entity _entity_1 = c.getEntity();
        String _name_1 = null;
        if (_entity_1!=null) {
          _name_1=_entity_1.getName();
        }
        String _plus = ("Entity \'" + _name_1);
        String _plus_1 = (_plus + "\' appears in more than one state condition in the same scenario");
        this.error(_plus_1, c, BddPackage.Literals.STATE_CONDITION__ENTITY, BddValidator.DUPLICATE_ENTITY_GUARD);
      }
    }
  }

  @Check
  public void checkConsistentTimeUnits(final Model m) {
    final Function1<Scenario, Trigger> _function = (Scenario s) -> {
      return s.getTrigger();
    };
    final Function1<Trigger, Boolean> _function_1 = (Trigger t) -> {
      return Boolean.valueOf((t != null));
    };
    final Function1<Trigger, Event> _function_2 = (Trigger t) -> {
      Event _switchResult = null;
      boolean _matched = false;
      if (t instanceof WhenTrigger) {
        _matched=true;
        _switchResult = ((WhenTrigger)t).getEvent();
      }
      if (!_matched) {
        if (t instanceof WheneverTrigger) {
          _matched=true;
          _switchResult = ((WheneverTrigger)t).getEvent();
        }
      }
      if (!_matched) {
        _switchResult = null;
      }
      return _switchResult;
    };
    final Function1<Event, Boolean> _function_3 = (Event e) -> {
      return Boolean.valueOf(((e != null) && e.eIsSet(e.eClass().getEStructuralFeature("time"))));
    };
    final Function1<Event, TimeEvent> _function_4 = (Event e) -> {
      return e.getTime();
    };
    final List<TimeEvent> timeEvents = IterableExtensions.<TimeEvent>toList(IterableExtensions.<Event, TimeEvent>map(IterableExtensions.<Event>filter(IterableExtensions.<Trigger, Event>map(IterableExtensions.<Trigger>filter(ListExtensions.<Scenario, Trigger>map(m.getScenarios(), _function), _function_1), _function_2), _function_3), _function_4));
    int _size = timeEvents.size();
    boolean _lessThan = (_size < 2);
    if (_lessThan) {
      return;
    }
    final TimeUnit firstUnit = IterableExtensions.<TimeEvent>head(timeEvents).getUnit();
    Iterable<TimeEvent> _tail = IterableExtensions.<TimeEvent>tail(timeEvents);
    for (final TimeEvent te : _tail) {
      TimeUnit _unit = te.getUnit();
      boolean _notEquals = (!Objects.equals(_unit, firstUnit));
      if (_notEquals) {
        String _unitLabel = this.unitLabel(firstUnit);
        String _plus = ("All scenarios must use the same time unit. Expected \'" + _unitLabel);
        String _plus_1 = (_plus + "\' but found \'");
        String _unitLabel_1 = this.unitLabel(te.getUnit());
        String _plus_2 = (_plus_1 + _unitLabel_1);
        String _plus_3 = (_plus_2 + 
          "\'. Consider changing this to \'");
        String _unitLabel_2 = this.unitLabel(firstUnit);
        String _plus_4 = (_plus_3 + _unitLabel_2);
        String _plus_5 = (_plus_4 + "\'");
        this.error(_plus_5, te, BddPackage.Literals.TIME_EVENT__UNIT, BddValidator.MIXED_TIME_UNITS);
      }
    }
  }

  @Check
  public void checkOnEndHasMatchingGiven(final Scenario s) {
    final EndEvent endEvent = this.getEndEvent(s);
    if ((endEvent == null)) {
      return;
    }
    final Function1<StateCondition, Boolean> _function = (StateCondition c) -> {
      return Boolean.valueOf((Objects.equals(c.getEntity(), endEvent.getEntity()) && Objects.equals(c.getState(), endEvent.getState())));
    };
    final StateCondition matchingGiven = IterableExtensions.<StateCondition>findFirst(Iterables.<StateCondition>filter(s.getGuards(), StateCondition.class), _function);
    if ((matchingGiven == null)) {
      Entity _entity = endEvent.getEntity();
      String _name = null;
      if (_entity!=null) {
        _name=_entity.getName();
      }
      String _plus = ("When using \'finishes\', the given clause must assert that \'" + _name);
      String _plus_1 = (_plus + "\' is in \'");
      dk.sdu.bdd.State _state = endEvent.getState();
      String _name_1 = null;
      if (_state!=null) {
        _name_1=_state.getName();
      }
      String _plus_2 = (_plus_1 + _name_1);
      String _plus_3 = (_plus_2 + 
        "\'. Add: \'and ");
      Entity _entity_1 = endEvent.getEntity();
      String _name_2 = null;
      if (_entity_1!=null) {
        _name_2=_entity_1.getName();
      }
      String _plus_4 = (_plus_3 + _name_2);
      String _plus_5 = (_plus_4 + " is in ");
      dk.sdu.bdd.State _state_1 = endEvent.getState();
      String _name_3 = null;
      if (_state_1!=null) {
        _name_3=_state_1.getName();
      }
      String _plus_6 = (_plus_5 + _name_3);
      String _plus_7 = (_plus_6 + "\'");
      this.error(_plus_7, endEvent, BddPackage.Literals.END_EVENT__ENTITY, BddValidator.ON_END_MISSING_GIVEN);
    }
  }

  @Check
  public void checkOnEndIsNotTrivial(final Scenario s) {
    final EndEvent endEvent = this.getEndEvent(s);
    if ((endEvent == null)) {
      return;
    }
    final StateCondition firstStateCond = IterableExtensions.<StateCondition>head(Iterables.<StateCondition>filter(s.getGuards(), StateCondition.class));
    if ((((firstStateCond != null) && 
      Objects.equals(endEvent.getEntity(), firstStateCond.getEntity())) && 
      Objects.equals(endEvent.getState(), firstStateCond.getState()))) {
      this.error("The \'finishes\' event cannot reference the same entity and state as the first given condition", endEvent, BddPackage.Literals.END_EVENT__ENTITY, BddValidator.ON_END_TRIVIAL);
    }
  }

  @Check
  public void checkNoDuplicateStateEffects(final Scenario s) {
    final HashSet<String> seen = CollectionLiterals.<String>newHashSet();
    Iterable<StateEffect> _filter = Iterables.<StateEffect>filter(s.getEffects(), StateEffect.class);
    for (final StateEffect e : _filter) {
      Entity _entity = e.getEntity();
      String _name = null;
      if (_entity!=null) {
        _name=_entity.getName();
      }
      boolean _add = seen.add(_name);
      boolean _not = (!_add);
      if (_not) {
        Entity _entity_1 = e.getEntity();
        String _name_1 = null;
        if (_entity_1!=null) {
          _name_1=_entity_1.getName();
        }
        String _plus = ("State of entity \'" + _name_1);
        String _plus_1 = (_plus + "\' is assigned more than once in the same scenario");
        this.error(_plus_1, e, BddPackage.Literals.STATE_EFFECT__ENTITY, BddValidator.DUPLICATE_STATE_EFFECT);
      }
    }
  }

  @Check
  public void checkNoDuplicatePropEffects(final Scenario s) {
    final HashSet<String> seen = CollectionLiterals.<String>newHashSet();
    Iterable<AssignEffect> _filter = Iterables.<AssignEffect>filter(s.getEffects(), AssignEffect.class);
    for (final AssignEffect e : _filter) {
      boolean _add = seen.add(this.propKey(e.getRef()));
      boolean _not = (!_add);
      if (_not) {
        String _propKey = this.propKey(e.getRef());
        String _plus = ("Property \'" + _propKey);
        String _plus_1 = (_plus + "\' is assigned more than once in the same scenario");
        this.error(_plus_1, e, BddPackage.Literals.ASSIGN_EFFECT__REF, BddValidator.DUPLICATE_PROP_EFFECT);
      }
    }
    Iterable<RandomEffect> _filter_1 = Iterables.<RandomEffect>filter(s.getEffects(), RandomEffect.class);
    for (final RandomEffect e_1 : _filter_1) {
      boolean _add_1 = seen.add(this.propKey(e_1.getRef()));
      boolean _not_1 = (!_add_1);
      if (_not_1) {
        String _propKey_1 = this.propKey(e_1.getRef());
        String _plus_2 = ("Property \'" + _propKey_1);
        String _plus_3 = (_plus_2 + "\' is assigned more than once in the same scenario");
        this.error(_plus_3, e_1, BddPackage.Literals.RANDOM_EFFECT__REF, BddValidator.DUPLICATE_PROP_EFFECT);
      }
    }
  }

  @Check
  public void checkTimeBoundNotAllowed(final Requirement r) {
    final dk.sdu.bdd.Check check = r.getCheck();
    TimeBound _timeBound = r.getTimeBound();
    final boolean hasBound = (_timeBound != null);
    if (((check instanceof NeverCheck) && hasBound)) {
      this.error("\'never\' checks must not have a time bound", r, BddPackage.Literals.REQUIREMENT__TIME_BOUND, "dk.sdu.bdd.TimeBoundNotAllowed");
    }
    if ((((check instanceof CheckQuery) && hasBound) && (!((CheckQuery) check).isExistential()))) {
      this.error("Universally-quantified checks must not have a time bound", r, BddPackage.Literals.REQUIREMENT__TIME_BOUND, "dk.sdu.bdd.TimeBoundNotAllowed");
    }
  }

  @Check
  public void checkTimeBoundRequired(final Requirement r) {
    final dk.sdu.bdd.Check check = r.getCheck();
    if ((((check instanceof MaxCheck) || (check instanceof MinCheck)) && (r.getTimeBound() == null))) {
      this.error("\'maximum\' and \'minimum\' checks require a \'within\' time bound", r, BddPackage.Literals.REQUIREMENT__CHECK, "dk.sdu.bdd.TimeBoundRequired");
    }
  }

  private EndEvent getEndEvent(final Scenario s) {
    EndEvent _xblockexpression = null;
    {
      final Trigger trigger = s.getTrigger();
      Event _switchResult = null;
      boolean _matched = false;
      if (trigger instanceof WhenTrigger) {
        _matched=true;
        _switchResult = ((WhenTrigger)trigger).getEvent();
      }
      if (!_matched) {
        if (trigger instanceof WheneverTrigger) {
          _matched=true;
          _switchResult = ((WheneverTrigger)trigger).getEvent();
        }
      }
      if (!_matched) {
        _switchResult = null;
      }
      final Event concreteEvent = _switchResult;
      if ((concreteEvent == null)) {
        return null;
      }
      boolean _eIsSet = concreteEvent.eIsSet(concreteEvent.eClass().getEStructuralFeature("end"));
      boolean _not = (!_eIsSet);
      if (_not) {
        return null;
      }
      _xblockexpression = concreteEvent.getEnd();
    }
    return _xblockexpression;
  }

  private String propKey(final PropRef ref) {
    Entity _entity = null;
    if (ref!=null) {
      _entity=ref.getEntity();
    }
    String _name = null;
    if (_entity!=null) {
      _name=_entity.getName();
    }
    String _plus = (_name + ".");
    Property _prop = null;
    if (ref!=null) {
      _prop=ref.getProp();
    }
    String _name_1 = null;
    if (_prop!=null) {
      _name_1=_prop.getName();
    }
    return (_plus + _name_1);
  }

  private String unitLabel(final TimeUnit u) {
    String _switchResult = null;
    if (u != null) {
      switch (u) {
        case MILLISECONDS:
          _switchResult = "milliseconds";
          break;
        case SECONDS:
          _switchResult = "seconds";
          break;
        case MINUTES:
          _switchResult = "minutes";
          break;
        case HOURS:
          _switchResult = "hours";
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }
}
