package dk.sdu.translation;

import dk.sdu.model.AbstractModel;
import java.util.List;
import java.util.Objects;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class AbstractModelPrinter {
  public String print(final AbstractModel.Model m) {
    String _xblockexpression = null;
    {
      final Function1<AbstractModel.Entity, String> _function = (AbstractModel.Entity it) -> {
        return this.printEntity(it);
      };
      List<String> _map = ListExtensions.<AbstractModel.Entity, String>map(m.entities(), _function);
      String _indent = this.indent(1);
      String _plus = (",\n" + _indent);
      final String entities = IterableExtensions.join(_map, _plus);
      final Function1<AbstractModel.Scenario, String> _function_1 = (AbstractModel.Scenario it) -> {
        return this.printScenario(it);
      };
      List<String> _map_1 = ListExtensions.<AbstractModel.Scenario, String>map(m.scenarios(), _function_1);
      String _indent_1 = this.indent(1);
      String _plus_1 = (",\n" + _indent_1);
      final String scenarios = IterableExtensions.join(_map_1, _plus_1);
      final Function1<AbstractModel.Requirement, String> _function_2 = (AbstractModel.Requirement it) -> {
        return this.printRequirement(it);
      };
      List<String> _map_2 = ListExtensions.<AbstractModel.Requirement, String>map(m.requirements(), _function_2);
      String _indent_2 = this.indent(1);
      String _plus_2 = (",\n" + _indent_2);
      final String requirements = IterableExtensions.join(_map_2, _plus_2);
      String _xifexpression = null;
      boolean _isEmpty = m.requirements().isEmpty();
      if (_isEmpty) {
        _xifexpression = "";
      } else {
        String _indent_3 = this.indent(1);
        String _plus_3 = (",\n" + _indent_3);
        _xifexpression = (_plus_3 + requirements);
      }
      final String reqPart = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("model(");
      String _name = m.name();
      _builder.append(_name);
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      String _indent_4 = this.indent(1);
      _builder.append(_indent_4);
      _builder.append(entities);
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      String _indent_5 = this.indent(1);
      _builder.append(_indent_5);
      _builder.append(scenarios);
      _builder.append(reqPart);
      _builder.append(")");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  private String printEntity(final AbstractModel.Entity e) {
    String _xblockexpression = null;
    {
      final Function1<AbstractModel.Property, String> _function = (AbstractModel.Property it) -> {
        return this.printProperty(it);
      };
      List<String> _map = ListExtensions.<AbstractModel.Property, String>map(e.properties(), _function);
      String _indent = this.indent(2);
      String _plus = (",\n" + _indent);
      final String props = IterableExtensions.join(_map, _plus);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("entity(");
      String _name = e.name();
      _builder.append(_name);
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      String _indent_1 = this.indent(2);
      _builder.append(_indent_1);
      _builder.append(props);
      _builder.append(")");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  private String printProperty(final AbstractModel.Property p) {
    String _switchResult = null;
    boolean _matched = false;
    if (p instanceof AbstractModel.StatesProperty) {
      _matched=true;
      String _join = IterableExtensions.join(((AbstractModel.StatesProperty)p).stateNames(), ", ");
      String _plus = ("states = [" + _join);
      _switchResult = (_plus + "]");
    }
    if (!_matched) {
      if (p instanceof AbstractModel.StateProperty) {
        _matched=true;
        String _stateName = ((AbstractModel.StateProperty)p).stateName();
        _switchResult = ("state = " + _stateName);
      }
    }
    if (!_matched) {
      if (p instanceof AbstractModel.NumericProperty) {
        _matched=true;
        String _name = ((AbstractModel.NumericProperty)p).name();
        String _plus = (_name + " = ");
        int _value = ((AbstractModel.NumericProperty)p).value();
        _switchResult = (_plus + Integer.valueOf(_value));
      }
    }
    return _switchResult;
  }

  private String printScenario(final AbstractModel.Scenario s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("scenario(");
    String _name = s.name();
    _builder.append(_name);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    String _indent = this.indent(2);
    _builder.append(_indent);
    String _printGuard = this.printGuard(s.guard());
    _builder.append(_printGuard);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    String _indent_1 = this.indent(2);
    _builder.append(_indent_1);
    String _printEvent = this.printEvent(s.event());
    _builder.append(_printEvent);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    String _indent_2 = this.indent(2);
    _builder.append(_indent_2);
    String _printEffect = this.printEffect(s.effect());
    _builder.append(_printEffect);
    _builder.append(")");
    return _builder.toString();
  }

  private String printGuard(final AbstractModel.Guard g) {
    String _switchResult = null;
    boolean _matched = false;
    if (g instanceof AbstractModel.StateEquality) {
      _matched=true;
      String _entity = ((AbstractModel.StateEquality)g).entity();
      String _plus = (_entity + ".state = ");
      String _state = ((AbstractModel.StateEquality)g).state();
      _switchResult = (_plus + _state);
    }
    if (!_matched) {
      if (g instanceof AbstractModel.StateInequality) {
        _matched=true;
        String _entity = ((AbstractModel.StateInequality)g).entity();
        String _plus = (_entity + ".state \u2260 ");
        String _state = ((AbstractModel.StateInequality)g).state();
        _switchResult = (_plus + _state);
      }
    }
    if (!_matched) {
      if (g instanceof AbstractModel.PropComparison) {
        _matched=true;
        String _printPropRef = this.printPropRef(((AbstractModel.PropComparison)g).ref());
        String _plus = (_printPropRef + " ");
        String _printCompOp = this.printCompOp(((AbstractModel.PropComparison)g).op());
        String _plus_1 = (_plus + _printCompOp);
        String _plus_2 = (_plus_1 + " ");
        String _printValue = this.printValue(((AbstractModel.PropComparison)g).value());
        _switchResult = (_plus_2 + _printValue);
      }
    }
    if (!_matched) {
      if (g instanceof AbstractModel.BetweenGuard) {
        _matched=true;
        String _printPropRef = this.printPropRef(((AbstractModel.BetweenGuard)g).ref());
        String _plus = (_printPropRef + " \u2265 ");
        String _printValue = this.printValue(((AbstractModel.BetweenGuard)g).low());
        String _plus_1 = (_plus + _printValue);
        String _plus_2 = (_plus_1 + " \u2227 ");
        String _printPropRef_1 = this.printPropRef(((AbstractModel.BetweenGuard)g).ref());
        String _plus_3 = (_plus_2 + _printPropRef_1);
        String _plus_4 = (_plus_3 + " \u2264 ");
        String _printValue_1 = this.printValue(((AbstractModel.BetweenGuard)g).high());
        _switchResult = (_plus_4 + _printValue_1);
      }
    }
    if (!_matched) {
      if (g instanceof AbstractModel.ConjunctionGuard) {
        _matched=true;
        String _printGuard = this.printGuard(((AbstractModel.ConjunctionGuard)g).left());
        String _plus = (_printGuard + " \u2227 ");
        String _printGuard_1 = this.printGuard(((AbstractModel.ConjunctionGuard)g).right());
        _switchResult = (_plus + _printGuard_1);
      }
    }
    return _switchResult;
  }

  private String printEvent(final AbstractModel.Event ev) {
    String _xblockexpression = null;
    {
      final AbstractModel.ClockEvent clock = ev.clock();
      final AbstractModel.OnEnd onEnd = ev.onEnd();
      String _xifexpression = null;
      if ((clock != null)) {
        String _entity = clock.entity();
        String _plus = (_entity + ".clock = ");
        int _amount = clock.amount();
        _xifexpression = (_plus + Integer.valueOf(_amount));
      } else {
        _xifexpression = null;
      }
      final String clockPart = _xifexpression;
      String _xifexpression_1 = null;
      if ((onEnd != null)) {
        String _entity_1 = onEnd.entity();
        String _plus_1 = ("on_end(" + _entity_1);
        String _plus_2 = (_plus_1 + ", ");
        String _state = onEnd.state();
        String _plus_3 = (_plus_2 + _state);
        _xifexpression_1 = (_plus_3 + ")");
      } else {
        _xifexpression_1 = null;
      }
      final String endPart = _xifexpression_1;
      String _xifexpression_2 = null;
      if (((clockPart != null) && (endPart != null))) {
        _xifexpression_2 = ((clockPart + " & ") + endPart);
      } else {
        String _xifexpression_3 = null;
        if ((clockPart != null)) {
          _xifexpression_3 = clockPart;
        } else {
          _xifexpression_3 = endPart;
        }
        _xifexpression_2 = _xifexpression_3;
      }
      _xblockexpression = _xifexpression_2;
    }
    return _xblockexpression;
  }

  private String printEffect(final AbstractModel.Effect e) {
    String _switchResult = null;
    boolean _matched = false;
    if (e instanceof AbstractModel.StateAssign) {
      _matched=true;
      String _entity = ((AbstractModel.StateAssign)e).entity();
      String _plus = (_entity + ".state = ");
      String _state = ((AbstractModel.StateAssign)e).state();
      _switchResult = (_plus + _state);
    }
    if (!_matched) {
      if (e instanceof AbstractModel.PropAssign) {
        _matched=true;
        String _printPropRef = this.printPropRef(((AbstractModel.PropAssign)e).ref());
        String _plus = (_printPropRef + " = ");
        String _printValue = this.printValue(((AbstractModel.PropAssign)e).value());
        _switchResult = (_plus + _printValue);
      }
    }
    if (!_matched) {
      if (e instanceof AbstractModel.PropDelta) {
        _matched=true;
        String _xblockexpression = null;
        {
          String _xifexpression = null;
          AbstractModel.DeltaOp _op = ((AbstractModel.PropDelta)e).op();
          boolean _equals = Objects.equals(_op, AbstractModel.DeltaOp.INC);
          if (_equals) {
            _xifexpression = " + ";
          } else {
            _xifexpression = " - ";
          }
          final String op = _xifexpression;
          String _printPropRef = this.printPropRef(((AbstractModel.PropDelta)e).ref());
          String _plus = (_printPropRef + " = ");
          String _printPropRef_1 = this.printPropRef(((AbstractModel.PropDelta)e).ref());
          String _plus_1 = (_plus + _printPropRef_1);
          String _plus_2 = (_plus_1 + op);
          String _printValue = this.printValue(((AbstractModel.PropDelta)e).value());
          _xblockexpression = (_plus_2 + _printValue);
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (e instanceof AbstractModel.PropRandom) {
        _matched=true;
        String _printPropRef = this.printPropRef(((AbstractModel.PropRandom)e).ref());
        String _plus = (_printPropRef + " = random(");
        String _printValue = this.printValue(((AbstractModel.PropRandom)e).low());
        String _plus_1 = (_plus + _printValue);
        String _plus_2 = (_plus_1 + ", ");
        String _printValue_1 = this.printValue(((AbstractModel.PropRandom)e).high());
        String _plus_3 = (_plus_2 + _printValue_1);
        _switchResult = (_plus_3 + ")");
      }
    }
    if (!_matched) {
      if (e instanceof AbstractModel.SequenceEffect) {
        _matched=true;
        String _printEffect = this.printEffect(((AbstractModel.SequenceEffect)e).left());
        String _plus = (_printEffect + " ;\n");
        String _indent = this.indent(2);
        String _plus_1 = (_plus + _indent);
        String _printEffect_1 = this.printEffect(((AbstractModel.SequenceEffect)e).right());
        _switchResult = (_plus_1 + _printEffect_1);
      }
    }
    return _switchResult;
  }

  private String printPropRef(final AbstractModel.PropRef r) {
    String _entity = r.entity();
    String _plus = (_entity + ".");
    String _prop = r.prop();
    return (_plus + _prop);
  }

  private String printValue(final AbstractModel.Value v) {
    String _switchResult = null;
    boolean _matched = false;
    if (v instanceof AbstractModel.NumberValue) {
      _matched=true;
      _switchResult = String.valueOf(((AbstractModel.NumberValue)v).value());
    }
    if (!_matched) {
      if (v instanceof AbstractModel.PropValue) {
        _matched=true;
        _switchResult = this.printPropRef(((AbstractModel.PropValue)v).ref());
      }
    }
    return _switchResult;
  }

  private String printCompOp(final AbstractModel.CompOp op) {
    String _switchResult = null;
    if (op != null) {
      switch (op) {
        case EQ:
          _switchResult = "=";
          break;
        case NEQ:
          _switchResult = "\u2260";
          break;
        case GT:
          _switchResult = ">";
          break;
        case LT:
          _switchResult = "<";
          break;
        case GEQ:
          _switchResult = "\u2265";
          break;
        case LEQ:
          _switchResult = "\u2264";
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }

  private String indent(final int level) {
    return "  ".repeat(level);
  }

  private String printRequirement(final AbstractModel.Requirement r) {
    String _xblockexpression = null;
    {
      String _xifexpression = null;
      AbstractModel.TimeBound _timeBound = r.timeBound();
      boolean _tripleNotEquals = (_timeBound != null);
      if (_tripleNotEquals) {
        String _indent = this.indent(2);
        String _plus = (",\n" + _indent);
        String _plus_1 = (_plus + "within(");
        int _amount = r.timeBound().amount();
        String _plus_2 = (_plus_1 + Integer.valueOf(_amount));
        _xifexpression = (_plus_2 + ")");
      } else {
        _xifexpression = "";
      }
      final String tb = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("requirement(");
      String _name = r.name();
      _builder.append(_name);
      _builder.append(tb);
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      String _indent_1 = this.indent(2);
      _builder.append(_indent_1);
      String _printCheck = this.printCheck(r.check());
      _builder.append(_printCheck);
      _builder.append(")");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  private String printCheck(final AbstractModel.Check c) {
    String _switchResult = null;
    boolean _matched = false;
    if (c instanceof AbstractModel.CheckQuery) {
      _matched=true;
      String _xblockexpression = null;
      {
        String _xifexpression = null;
        boolean _existential = ((AbstractModel.CheckQuery)c).existential();
        if (_existential) {
          _xifexpression = "E";
        } else {
          _xifexpression = "A";
        }
        final String q = _xifexpression;
        String _xifexpression_1 = null;
        boolean _diamond = ((AbstractModel.CheckQuery)c).diamond();
        if (_diamond) {
          _xifexpression_1 = "<>";
        } else {
          _xifexpression_1 = "[]";
        }
        final String m = _xifexpression_1;
        String _printGuard = this.printGuard(((AbstractModel.CheckQuery)c).cond());
        String _plus = ((((("check(" + q) + ", ") + m) + ", ") + _printGuard);
        _xblockexpression = (_plus + ")");
      }
      _switchResult = _xblockexpression;
    }
    if (!_matched) {
      if (c instanceof AbstractModel.NeverCheck) {
        _matched=true;
        String _printGuard = this.printGuard(((AbstractModel.NeverCheck)c).cond());
        String _plus = ("never(" + _printGuard);
        _switchResult = (_plus + ")");
      }
    }
    if (!_matched) {
      if (c instanceof AbstractModel.MaxCheck) {
        _matched=true;
        String _printPropRef = this.printPropRef(((AbstractModel.MaxCheck)c).ref());
        String _plus = ("max(" + _printPropRef);
        _switchResult = (_plus + ")");
      }
    }
    if (!_matched) {
      if (c instanceof AbstractModel.MinCheck) {
        _matched=true;
        String _printPropRef = this.printPropRef(((AbstractModel.MinCheck)c).ref());
        String _plus = ("min(" + _printPropRef);
        _switchResult = (_plus + ")");
      }
    }
    return _switchResult;
  }
}
