package dk.sdu.translation;

import com.google.common.collect.Iterables;
import dk.sdu.bdd.AssignEffect;
import dk.sdu.bdd.BetweenCondition;
import dk.sdu.bdd.Check;
import dk.sdu.bdd.CheckQuery;
import dk.sdu.bdd.Comp;
import dk.sdu.bdd.Condition;
import dk.sdu.bdd.DeltaEffect;
import dk.sdu.bdd.DeltaOp;
import dk.sdu.bdd.Effect;
import dk.sdu.bdd.EndEvent;
import dk.sdu.bdd.Entity;
import dk.sdu.bdd.Event;
import dk.sdu.bdd.MaxCheck;
import dk.sdu.bdd.MinCheck;
import dk.sdu.bdd.Model;
import dk.sdu.bdd.NeverCheck;
import dk.sdu.bdd.NumberValue;
import dk.sdu.bdd.PropCondition;
import dk.sdu.bdd.PropRef;
import dk.sdu.bdd.PropValue;
import dk.sdu.bdd.Property;
import dk.sdu.bdd.RandomEffect;
import dk.sdu.bdd.Requirement;
import dk.sdu.bdd.Scenario;
import dk.sdu.bdd.State;
import dk.sdu.bdd.StateCondition;
import dk.sdu.bdd.StateEffect;
import dk.sdu.bdd.TimeBound;
import dk.sdu.bdd.Trigger;
import dk.sdu.bdd.Value;
import dk.sdu.bdd.WhenTrigger;
import dk.sdu.bdd.WheneverTrigger;
import dk.sdu.model.AbstractModel;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

@SuppressWarnings("all")
public class ConcreteToAbstract {
  public AbstractModel.Model translateModel(final Model m) {
    String _name = m.getName();
    final Function1<Entity, AbstractModel.Entity> _function = (Entity it) -> {
      return this.translateEntity(it);
    };
    List<AbstractModel.Entity> _map = ListExtensions.<Entity, AbstractModel.Entity>map(m.getEntities(), _function);
    final Function1<Scenario, AbstractModel.Scenario> _function_1 = (Scenario it) -> {
      return this.translateScenario(it);
    };
    List<AbstractModel.Scenario> _map_1 = ListExtensions.<Scenario, AbstractModel.Scenario>map(m.getScenarios(), _function_1);
    final Function1<Requirement, AbstractModel.Requirement> _function_2 = (Requirement it) -> {
      return this.translateRequirement(it);
    };
    List<AbstractModel.Requirement> _map_2 = ListExtensions.<Requirement, AbstractModel.Requirement>map(m.getRequirements(), _function_2);
    return new AbstractModel.Model(_name, _map, _map_1, _map_2);
  }

  public AbstractModel.Entity translateEntity(final Entity e) {
    AbstractModel.Entity _xblockexpression = null;
    {
      final Function1<State, String> _function = (State it) -> {
        return it.getName();
      };
      final List<String> stateNames = ListExtensions.<State, String>map(e.getStates(), _function);
      AbstractModel.StatesProperty _statesProperty = new AbstractModel.StatesProperty(stateNames);
      String _get = stateNames.get(0);
      AbstractModel.StateProperty _stateProperty = new AbstractModel.StateProperty(_get);
      final List<AbstractModel.Property> propsHead = List.<AbstractModel.Property>of(_statesProperty, _stateProperty);
      final Function1<Property, AbstractModel.Property> _function_1 = (Property it) -> {
        AbstractModel.NumericProperty _translateProperty = this.translateProperty(it);
        return ((AbstractModel.Property) _translateProperty);
      };
      final List<AbstractModel.Property> propsTail = IterableExtensions.<AbstractModel.Property>toList(ListExtensions.<Property, AbstractModel.Property>map(e.getProperties(), _function_1));
      String _name = e.getName();
      List<AbstractModel.Property> _list = IterableExtensions.<AbstractModel.Property>toList(Iterables.<AbstractModel.Property>concat(propsHead, propsTail));
      _xblockexpression = new AbstractModel.Entity(_name, _list);
    }
    return _xblockexpression;
  }

  public AbstractModel.NumericProperty translateProperty(final Property p) {
    String _name = p.getName();
    int _value = p.getValue();
    return new AbstractModel.NumericProperty(_name, _value);
  }

  public AbstractModel.Scenario translateScenario(final Scenario s) {
    AbstractModel.Scenario _xblockexpression = null;
    {
      final StateCondition firstStateCond = IterableExtensions.<StateCondition>head(Iterables.<StateCondition>filter(s.getGuards(), StateCondition.class));
      final String e0 = firstStateCond.getEntity().getName();
      final String s0 = firstStateCond.getState().getName();
      final Function1<Condition, AbstractModel.Guard> _function = (Condition it) -> {
        return this.translateCondition(it);
      };
      final AbstractModel.Guard guard = this.foldGuards(IterableExtensions.<AbstractModel.Guard>toList(ListExtensions.<Condition, AbstractModel.Guard>map(s.getGuards(), _function)));
      final AbstractModel.Event event = this.translateEvent(s, e0);
      final Function1<Effect, AbstractModel.Effect> _function_1 = (Effect it) -> {
        return this.translateEffect(it);
      };
      final AbstractModel.Effect rawFx = this.foldEffects(IterableExtensions.<AbstractModel.Effect>toList(ListExtensions.<Effect, AbstractModel.Effect>map(s.getEffects(), _function_1)));
      AbstractModel.Effect _xifexpression = null;
      Trigger _trigger = s.getTrigger();
      if ((_trigger instanceof WheneverTrigger)) {
        AbstractModel.StateAssign _stateAssign = new AbstractModel.StateAssign(e0, s0);
        _xifexpression = new AbstractModel.SequenceEffect(_stateAssign, rawFx);
      } else {
        _xifexpression = rawFx;
      }
      final AbstractModel.Effect effect = _xifexpression;
      String _name = s.getName();
      _xblockexpression = new AbstractModel.Scenario(_name, guard, event, effect);
    }
    return _xblockexpression;
  }

  public AbstractModel.Guard foldGuards(final List<AbstractModel.Guard> guards) {
    final Function2<AbstractModel.Guard, AbstractModel.Guard, AbstractModel.Guard> _function = (AbstractModel.Guard acc, AbstractModel.Guard g) -> {
      return new AbstractModel.ConjunctionGuard(acc, g);
    };
    return IterableExtensions.<AbstractModel.Guard>reduce(guards, _function);
  }

  public AbstractModel.Effect foldEffects(final List<AbstractModel.Effect> effects) {
    final Function2<AbstractModel.Effect, AbstractModel.Effect, AbstractModel.Effect> _function = (AbstractModel.Effect acc, AbstractModel.Effect f) -> {
      return new AbstractModel.SequenceEffect(acc, f);
    };
    return IterableExtensions.<AbstractModel.Effect>reduce(effects, _function);
  }

  protected AbstractModel.Guard _translateCondition(final StateCondition c) {
    Record _xifexpression = null;
    boolean _isNegated = c.isNegated();
    if (_isNegated) {
      String _name = c.getEntity().getName();
      String _name_1 = c.getState().getName();
      _xifexpression = new AbstractModel.StateInequality(_name, _name_1);
    } else {
      String _name_2 = c.getEntity().getName();
      String _name_3 = c.getState().getName();
      _xifexpression = new AbstractModel.StateEquality(_name_2, _name_3);
    }
    return ((AbstractModel.Guard)_xifexpression);
  }

  protected AbstractModel.Guard _translateCondition(final PropCondition c) {
    AbstractModel.PropRef _translatePropRef = this.translatePropRef(c.getRef());
    AbstractModel.CompOp _translateComp = this.translateComp(c.getComp());
    AbstractModel.Value _translateValue = this.translateValue(c.getValue());
    return new AbstractModel.PropComparison(_translatePropRef, _translateComp, _translateValue);
  }

  protected AbstractModel.Guard _translateCondition(final BetweenCondition c) {
    AbstractModel.PropRef _translatePropRef = this.translatePropRef(c.getRef());
    AbstractModel.Value _translateValue = this.translateValue(c.getLow());
    AbstractModel.Value _translateValue_1 = this.translateValue(c.getHigh());
    return new AbstractModel.BetweenGuard(_translatePropRef, _translateValue, _translateValue_1);
  }

  public AbstractModel.Event translateEvent(final Scenario s, final String e0) {
    AbstractModel.Event _xblockexpression = null;
    {
      Event _switchResult = null;
      Trigger _trigger = s.getTrigger();
      boolean _matched = false;
      if (_trigger instanceof WhenTrigger) {
        _matched=true;
        _switchResult = s.getTrigger().getEvent();
      }
      if (!_matched) {
        if (_trigger instanceof WheneverTrigger) {
          _matched=true;
          _switchResult = s.getTrigger().getEvent();
        }
      }
      final Event concreteEvent = _switchResult;
      final boolean hasTime = concreteEvent.eIsSet(concreteEvent.eClass().getEStructuralFeature("time"));
      final boolean hasEnd = concreteEvent.eIsSet(concreteEvent.eClass().getEStructuralFeature("end"));
      AbstractModel.ClockEvent _xifexpression = null;
      if (hasTime) {
        int _amount = concreteEvent.getTime().getAmount();
        _xifexpression = new AbstractModel.ClockEvent(e0, _amount);
      } else {
        _xifexpression = null;
      }
      final AbstractModel.ClockEvent clock = _xifexpression;
      AbstractModel.OnEnd _xifexpression_1 = null;
      if (hasEnd) {
        _xifexpression_1 = this.translateEndEvent(concreteEvent.getEnd());
      } else {
        _xifexpression_1 = null;
      }
      final AbstractModel.OnEnd onEnd = _xifexpression_1;
      _xblockexpression = new AbstractModel.Event(clock, onEnd);
    }
    return _xblockexpression;
  }

  public AbstractModel.OnEnd translateEndEvent(final EndEvent e) {
    String _name = e.getEntity().getName();
    String _name_1 = e.getState().getName();
    return new AbstractModel.OnEnd(_name, _name_1);
  }

  protected AbstractModel.Effect _translateEffect(final StateEffect e) {
    String _name = e.getEntity().getName();
    String _name_1 = e.getState().getName();
    return new AbstractModel.StateAssign(_name, _name_1);
  }

  protected AbstractModel.Effect _translateEffect(final AssignEffect e) {
    AbstractModel.PropRef _translatePropRef = this.translatePropRef(e.getRef());
    AbstractModel.Value _translateValue = this.translateValue(e.getValue());
    return new AbstractModel.PropAssign(_translatePropRef, _translateValue);
  }

  protected AbstractModel.Effect _translateEffect(final DeltaEffect e) {
    AbstractModel.PropRef _translatePropRef = this.translatePropRef(e.getRef());
    AbstractModel.DeltaOp _xifexpression = null;
    DeltaOp _op = e.getOp();
    boolean _equals = Objects.equals(_op, DeltaOp.INC);
    if (_equals) {
      _xifexpression = AbstractModel.DeltaOp.INC;
    } else {
      _xifexpression = AbstractModel.DeltaOp.DEC;
    }
    AbstractModel.Value _translateValue = this.translateValue(e.getValue());
    return new AbstractModel.PropDelta(_translatePropRef, _xifexpression, _translateValue);
  }

  protected AbstractModel.Effect _translateEffect(final RandomEffect e) {
    AbstractModel.PropRef _translatePropRef = this.translatePropRef(e.getRef());
    AbstractModel.Value _translateValue = this.translateValue(e.getLow());
    AbstractModel.Value _translateValue_1 = this.translateValue(e.getHigh());
    return new AbstractModel.PropRandom(_translatePropRef, _translateValue, _translateValue_1);
  }

  public AbstractModel.PropRef translatePropRef(final PropRef r) {
    String _name = r.getEntity().getName();
    String _name_1 = r.getProp().getName();
    return new AbstractModel.PropRef(_name, _name_1);
  }

  protected AbstractModel.Value _translateValue(final NumberValue v) {
    int _value = v.getValue();
    return new AbstractModel.NumberValue(_value);
  }

  protected AbstractModel.Value _translateValue(final PropValue v) {
    AbstractModel.PropRef _translatePropRef = this.translatePropRef(v.getRef());
    return new AbstractModel.PropValue(_translatePropRef);
  }

  public AbstractModel.CompOp translateComp(final Comp c) {
    AbstractModel.CompOp _switchResult = null;
    if (c != null) {
      switch (c) {
        case EQ:
          _switchResult = AbstractModel.CompOp.EQ;
          break;
        case NEQ:
          _switchResult = AbstractModel.CompOp.NEQ;
          break;
        case GT:
          _switchResult = AbstractModel.CompOp.GT;
          break;
        case LT:
          _switchResult = AbstractModel.CompOp.LT;
          break;
        case GEQ:
          _switchResult = AbstractModel.CompOp.GEQ;
          break;
        case LEQ:
          _switchResult = AbstractModel.CompOp.LEQ;
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }

  public AbstractModel.Requirement translateRequirement(final Requirement r) {
    AbstractModel.Requirement _xblockexpression = null;
    {
      AbstractModel.TimeBound _xifexpression = null;
      TimeBound _timeBound = r.getTimeBound();
      boolean _tripleNotEquals = (_timeBound != null);
      if (_tripleNotEquals) {
        int _amount = r.getTimeBound().getAmount();
        _xifexpression = new AbstractModel.TimeBound(_amount);
      } else {
        _xifexpression = null;
      }
      final AbstractModel.TimeBound timeBound = _xifexpression;
      final AbstractModel.Check check = this.translateCheck(r.getCheck());
      String _name = r.getName();
      _xblockexpression = new AbstractModel.Requirement(_name, timeBound, check);
    }
    return _xblockexpression;
  }

  public AbstractModel.Check translateCheck(final Check c) {
    Record _switchResult = null;
    boolean _matched = false;
    if (c instanceof CheckQuery) {
      _matched=true;
      boolean _isExistential = ((CheckQuery)c).isExistential();
      boolean _isDiamond = ((CheckQuery)c).isDiamond();
      AbstractModel.Guard _translateReqCond = this.translateReqCond(((CheckQuery)c).eGet(((CheckQuery)c).eClass().getEStructuralFeature("cond")));
      _switchResult = new AbstractModel.CheckQuery(_isExistential, _isDiamond, _translateReqCond);
    }
    if (!_matched) {
      if (c instanceof NeverCheck) {
        _matched=true;
        AbstractModel.Guard _translateReqCond = this.translateReqCond(((NeverCheck)c).eGet(((NeverCheck)c).eClass().getEStructuralFeature("cond")));
        _switchResult = new AbstractModel.NeverCheck(_translateReqCond);
      }
    }
    if (!_matched) {
      if (c instanceof MaxCheck) {
        _matched=true;
        AbstractModel.PropRef _translatePropRef = this.translatePropRef(((MaxCheck)c).getRef());
        _switchResult = new AbstractModel.MaxCheck(_translatePropRef);
      }
    }
    if (!_matched) {
      if (c instanceof MinCheck) {
        _matched=true;
        AbstractModel.PropRef _translatePropRef = this.translatePropRef(((MinCheck)c).getRef());
        _switchResult = new AbstractModel.MinCheck(_translatePropRef);
      }
    }
    return ((AbstractModel.Check)_switchResult);
  }

  public AbstractModel.Guard translateReqCond(final Object cond) {
    if ((cond instanceof StateCondition)) {
      return this.translateCondition(((StateCondition) cond));
    }
    if ((cond instanceof PropCondition)) {
      return this.translateCondition(((PropCondition) cond));
    }
    if ((cond instanceof BetweenCondition)) {
      return this.translateCondition(((BetweenCondition) cond));
    }
    Class<?> _class = null;
    if (cond!=null) {
      _class=cond.getClass();
    }
    String _plus = ("Unknown condition type: " + _class);
    throw new IllegalArgumentException(_plus);
  }

  @XbaseGenerated
  public AbstractModel.Guard translateCondition(final EObject c) {
    if (c instanceof BetweenCondition) {
      return _translateCondition((BetweenCondition)c);
    } else if (c instanceof PropCondition) {
      return _translateCondition((PropCondition)c);
    } else if (c instanceof StateCondition) {
      return _translateCondition((StateCondition)c);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(c).toString());
    }
  }

  @XbaseGenerated
  public AbstractModel.Effect translateEffect(final Effect e) {
    if (e instanceof AssignEffect) {
      return _translateEffect((AssignEffect)e);
    } else if (e instanceof DeltaEffect) {
      return _translateEffect((DeltaEffect)e);
    } else if (e instanceof RandomEffect) {
      return _translateEffect((RandomEffect)e);
    } else if (e instanceof StateEffect) {
      return _translateEffect((StateEffect)e);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(e).toString());
    }
  }

  @XbaseGenerated
  public AbstractModel.Value translateValue(final Value v) {
    if (v instanceof NumberValue) {
      return _translateValue((NumberValue)v);
    } else if (v instanceof PropValue) {
      return _translateValue((PropValue)v);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(v).toString());
    }
  }
}
