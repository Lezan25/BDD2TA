package dk.sdu.generator;

import com.google.common.collect.Iterables;
import dk.sdu.model.AbstractModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class TranslateAbstractToUppaal {
  private int idCounter = 0;

  private final HashMap<String, String> locationIds = new HashMap<String, String>();

  private final HashMap<String, List<String>> receiverEdges = new HashMap<String, List<String>>();

  public String generate(final AbstractModel.Model m) {
    String _xblockexpression = null;
    {
      this.idCounter = 0;
      this.locationIds.clear();
      this.receiverEdges.clear();
      List<AbstractModel.Entity> _entities = m.entities();
      for (final AbstractModel.Entity e : _entities) {
        {
          final AbstractModel.StatesProperty statesProp = IterableExtensions.<AbstractModel.StatesProperty>head(Iterables.<AbstractModel.StatesProperty>filter(e.properties(), AbstractModel.StatesProperty.class));
          List<String> _stateNames = statesProp.stateNames();
          for (final String s : _stateNames) {
            this.locationIds.put(this.locKey(e.name(), s), this.nextId());
          }
          final Function1<AbstractModel.Scenario, Boolean> _function = (AbstractModel.Scenario sc) -> {
            return Boolean.valueOf((this.hasOnEnd(sc) && Objects.equals(this.onEndEntity(sc), e.name())));
          };
          Iterable<AbstractModel.Scenario> _filter = IterableExtensions.<AbstractModel.Scenario>filter(m.scenarios(), _function);
          for (final AbstractModel.Scenario sc : _filter) {
            this.locationIds.put(this.finishingLocKey(e.name(), sc), this.nextId());
          }
          String _name = e.name();
          ArrayList<String> _arrayList = new ArrayList<String>();
          this.receiverEdges.put(_name, _arrayList);
        }
      }
      final String globalDecls = this.generateGlobalDecls(m);
      final Function1<AbstractModel.Entity, String> _function = (AbstractModel.Entity it) -> {
        return this.generateTemplate(it, m);
      };
      final String templates = IterableExtensions.join(ListExtensions.<AbstractModel.Entity, String>map(m.entities(), _function), "\n");
      final Function1<AbstractModel.Entity, String> _function_1 = (AbstractModel.Entity e_1) -> {
        String _name = e_1.name();
        String _plus = (_name + "_1 = ");
        String _name_1 = e_1.name();
        String _plus_1 = (_plus + _name_1);
        return (_plus_1 + "();");
      };
      final String instantiations = IterableExtensions.join(ListExtensions.<AbstractModel.Entity, String>map(m.entities(), _function_1), "\n");
      final Function1<AbstractModel.Entity, String> _function_2 = (AbstractModel.Entity e_1) -> {
        String _name = e_1.name();
        return (_name + "_1");
      };
      final String systemLine = IterableExtensions.join(ListExtensions.<AbstractModel.Entity, String>map(m.entities(), _function_2), ", ");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      _builder.newLine();
      _builder.append("<!DOCTYPE nta PUBLIC \'-//Uppaal Team//DTD Flat System 1.6//EN\' \'http://www.it.uu.se/research/group/darts/uppaal/flat-1_6.dtd\'>");
      _builder.newLine();
      _builder.append("<nta>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<declaration>");
      _builder.append(globalDecls, "\t");
      _builder.append("</declaration>");
      _builder.newLineIfNotEmpty();
      _builder.append(templates);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("<system>");
      _builder.newLine();
      _builder.append(instantiations);
      _builder.newLineIfNotEmpty();
      _builder.append("system ");
      _builder.append(systemLine);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("</system>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<queries>");
      _builder.newLine();
      String _generateQueries = this.generateQueries(m);
      _builder.append(_generateQueries);
      _builder.append("\t</queries>");
      _builder.newLineIfNotEmpty();
      _builder.append("</nta>");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  private String generateGlobalDecls(final AbstractModel.Model m) {
    String _xblockexpression = null;
    {
      final StringBuilder sb = new StringBuilder();
      List<AbstractModel.Entity> _entities = m.entities();
      for (final AbstractModel.Entity e : _entities) {
        {
          final AbstractModel.StatesProperty statesProp = IterableExtensions.<AbstractModel.StatesProperty>head(Iterables.<AbstractModel.StatesProperty>filter(e.properties(), AbstractModel.StatesProperty.class));
          final List<String> stateNames = statesProp.stateNames();
          String _name = e.name();
          final String typeName = (_name + "_state_type");
          int _size = stateNames.size();
          int _minus = (_size - 1);
          String _plus = ("typedef int[0," + Integer.valueOf(_minus));
          String _plus_1 = (_plus + "] ");
          String _plus_2 = (_plus_1 + typeName);
          String _plus_3 = (_plus_2 + ";\n");
          sb.append(_plus_3);
          for (int i = 0; (i < stateNames.size()); i++) {
            String _stateConst = this.stateConst(e.name(), stateNames.get(i));
            String _plus_4 = ((("const " + typeName) + " ") + _stateConst);
            String _plus_5 = (_plus_4 + " = ");
            String _plus_6 = (_plus_5 + Integer.valueOf(i));
            String _plus_7 = (_plus_6 + ";\n");
            sb.append(_plus_7);
          }
          String _stateVar = this.stateVar(e.name());
          String _plus_4 = ("int " + _stateVar);
          String _plus_5 = (_plus_4 + " = ");
          String _stateConst = this.stateConst(e.name(), stateNames.get(0));
          String _plus_6 = (_plus_5 + _stateConst);
          String _plus_7 = (_plus_6 + ";\n");
          sb.append(_plus_7);
          Iterable<AbstractModel.NumericProperty> _filter = Iterables.<AbstractModel.NumericProperty>filter(e.properties(), AbstractModel.NumericProperty.class);
          for (final AbstractModel.NumericProperty p : _filter) {
            String _propVar = this.propVar(e.name(), p.name());
            String _plus_8 = ("int " + _propVar);
            String _plus_9 = (_plus_8 + " = ");
            int _value = p.value();
            String _plus_10 = (_plus_9 + Integer.valueOf(_value));
            String _plus_11 = (_plus_10 + ";\n");
            sb.append(_plus_11);
          }
          sb.append("\n");
        }
      }
      List<AbstractModel.Scenario> _scenarios = m.scenarios();
      for (final AbstractModel.Scenario sc : _scenarios) {
        {
          final String e0 = this.ownerOf(sc);
          String _xifexpression = null;
          boolean _hasOnEnd = this.hasOnEnd(sc);
          if (_hasOnEnd) {
            _xifexpression = this.onEndEntity(sc);
          } else {
            _xifexpression = null;
          }
          final String onEndEnt = _xifexpression;
          if (((onEndEnt != null) && (!Objects.equals(e0, onEndEnt)))) {
            String _name = sc.name();
            String _plus = ("broadcast chan " + _name);
            String _plus_1 = (_plus + "_trigger;\n");
            sb.append(_plus_1);
          }
          List<AbstractModel.StateAssign> _xifexpression_1 = null;
          if ((onEndEnt != null)) {
            final Function1<AbstractModel.StateAssign, Boolean> _function = (AbstractModel.StateAssign sa) -> {
              String _entity = sa.entity();
              return Boolean.valueOf((!Objects.equals(_entity, onEndEnt)));
            };
            _xifexpression_1 = IterableExtensions.<AbstractModel.StateAssign>toList(IterableExtensions.<AbstractModel.StateAssign>filter(this.collectStateAssigns(sc.effect()), _function));
          } else {
            final Function1<AbstractModel.StateAssign, Boolean> _function_1 = (AbstractModel.StateAssign sa) -> {
              String _entity = sa.entity();
              return Boolean.valueOf((!Objects.equals(_entity, e0)));
            };
            _xifexpression_1 = IterableExtensions.<AbstractModel.StateAssign>toList(IterableExtensions.<AbstractModel.StateAssign>filter(this.collectStateAssigns(sc.effect()), _function_1));
          }
          final List<AbstractModel.StateAssign> nonEStateAssigns = _xifexpression_1;
          boolean _isEmpty = nonEStateAssigns.isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            String _name_1 = sc.name();
            String _plus_2 = ("broadcast chan " + _name_1);
            String _plus_3 = (_plus_2 + ";\n");
            sb.append(_plus_3);
          }
        }
      }
      _xblockexpression = sb.toString();
    }
    return _xblockexpression;
  }

  private String generateTemplate(final AbstractModel.Entity e, final AbstractModel.Model m) {
    String _xblockexpression = null;
    {
      final AbstractModel.StatesProperty statesProp = IterableExtensions.<AbstractModel.StatesProperty>head(Iterables.<AbstractModel.StatesProperty>filter(e.properties(), AbstractModel.StatesProperty.class));
      final List<String> stateNames = statesProp.stateNames();
      final Function1<AbstractModel.Scenario, Boolean> _function = (AbstractModel.Scenario sc) -> {
        String _ownerOf = this.ownerOf(sc);
        String _name = e.name();
        return Boolean.valueOf(Objects.equals(_ownerOf, _name));
      };
      final List<AbstractModel.Scenario> ownedScenarios = IterableExtensions.<AbstractModel.Scenario>toList(IterableExtensions.<AbstractModel.Scenario>filter(m.scenarios(), _function));
      final Function1<AbstractModel.Scenario, Boolean> _function_1 = (AbstractModel.Scenario sc) -> {
        return Boolean.valueOf((this.hasOnEnd(sc) && Objects.equals(this.onEndEntity(sc), e.name())));
      };
      final List<AbstractModel.Scenario> onEndScenarios = IterableExtensions.<AbstractModel.Scenario>toList(IterableExtensions.<AbstractModel.Scenario>filter(m.scenarios(), _function_1));
      final Function1<AbstractModel.Scenario, Boolean> _function_2 = (AbstractModel.Scenario sc) -> {
        boolean _hasOnEnd = this.hasOnEnd(sc);
        return Boolean.valueOf((!_hasOnEnd));
      };
      final List<AbstractModel.Scenario> regularScenarios = IterableExtensions.<AbstractModel.Scenario>toList(IterableExtensions.<AbstractModel.Scenario>filter(ownedScenarios, _function_2));
      final LinkedHashSet<Integer> clockValues = new LinkedHashSet<Integer>();
      for (final AbstractModel.Scenario sc : ownedScenarios) {
        if (((sc.event().clock() != null) && (sc.event().clock().amount() > 0))) {
          clockValues.add(Integer.valueOf(sc.event().clock().amount()));
        }
      }
      final Function1<Integer, String> _function_3 = (Integer v) -> {
        String _clockName = this.clockName((v).intValue());
        return (_clockName + " = 0");
      };
      final String allClockResets = IterableExtensions.join(IterableExtensions.<Integer, String>map(clockValues, _function_3), ", ");
      final ArrayList<String> locations = new ArrayList<String>();
      final ArrayList<String> edges = new ArrayList<String>();
      int x = 0;
      for (final String s : stateNames) {
        {
          final boolean isUrgent = this.isUrgentLocation(e.name(), s, ownedScenarios);
          final String invariant = this.getLocationInvariant(e.name(), s, ownedScenarios);
          locations.add(this.buildLocation(this.locationIds.get(this.locKey(e.name(), s)), s, x, isUrgent, invariant));
          int _x = x;
          x = (_x + 200);
        }
      }
      for (final AbstractModel.Scenario sc_1 : onEndScenarios) {
        {
          final String finId = this.locationIds.get(this.finishingLocKey(e.name(), sc_1));
          final String srcState = this.onEndState(sc_1);
          final String invariant = this.getLocationInvariant(e.name(), srcState, ownedScenarios);
          String _name = sc_1.name();
          String _plus = ((srcState + "_finishing_") + _name);
          locations.add(this.buildLocation(finId, _plus, x, false, invariant));
          int _x = x;
          x = (_x + 200);
        }
      }
      final String initLocId = this.locationIds.get(this.locKey(e.name(), stateNames.get(0)));
      for (final AbstractModel.Scenario sc_2 : regularScenarios) {
        {
          final String sourceState = this.getSourceState(sc_2.guard(), e.name());
          final String sourceLocId = this.locationIds.get(this.locKey(e.name(), sourceState));
          final Function1<AbstractModel.StateAssign, Boolean> _function_4 = (AbstractModel.StateAssign sa) -> {
            String _entity = sa.entity();
            String _name = e.name();
            return Boolean.valueOf(Objects.equals(_entity, _name));
          };
          final AbstractModel.StateAssign primaryAssign = IterableExtensions.<AbstractModel.StateAssign>findFirst(this.collectStateAssigns(sc_2.effect()), _function_4);
          String _xifexpression = null;
          if ((primaryAssign != null)) {
            _xifexpression = primaryAssign.state();
          } else {
            _xifexpression = sourceState;
          }
          final String targetState = _xifexpression;
          final String targetLocId = this.locationIds.get(this.locKey(e.name(), targetState));
          final Function1<AbstractModel.StateAssign, Boolean> _function_5 = (AbstractModel.StateAssign sa) -> {
            String _entity = sa.entity();
            String _name = e.name();
            return Boolean.valueOf((!Objects.equals(_entity, _name)));
          };
          final List<AbstractModel.StateAssign> affectedNonE = IterableExtensions.<AbstractModel.StateAssign>toList(IterableExtensions.<AbstractModel.StateAssign>filter(this.collectStateAssigns(sc_2.effect()), _function_5));
          boolean _isEmpty = affectedNonE.isEmpty();
          final boolean needsChan = (!_isEmpty);
          String _xifexpression_1 = null;
          if (needsChan) {
            String _name = sc_2.name();
            _xifexpression_1 = (_name + "!");
          } else {
            _xifexpression_1 = null;
          }
          final String syncStr = _xifexpression_1;
          final String guardStr = this.buildGuard(sc_2.guard(), sc_2.event(), e.name());
          final String updateStr = this.buildUpdate(sc_2.effect(), e.name(), allClockResets);
          final String selectStr = this.buildSelect(sc_2.effect());
          edges.add(this.buildTransition(this.nextId(), sourceLocId, targetLocId, selectStr, guardStr, syncStr, updateStr));
          if (needsChan) {
            for (final AbstractModel.StateAssign sa : affectedNonE) {
              {
                final List<String> recvList = this.receiverEdges.get(sa.entity());
                if ((recvList != null)) {
                  this.addReceiverEdgesForEntity(sc_2, sa.entity(), recvList, m);
                }
              }
            }
          }
        }
      }
      for (final String s_1 : stateNames) {
        {
          final Function1<AbstractModel.Scenario, Boolean> _function_4 = (AbstractModel.Scenario sc_3) -> {
            return Boolean.valueOf((((sc_3.event().clock() != null) && (sc_3.event().clock().amount() > 0)) && Objects.equals(this.getSourceState(sc_3.guard(), e.name()), s_1)));
          };
          final Function1<AbstractModel.Scenario, Integer> _function_5 = (AbstractModel.Scenario sc_3) -> {
            return Integer.valueOf(sc_3.event().clock().amount());
          };
          final Set<Integer> clocksForState = IterableExtensions.<Integer>toSet(IterableExtensions.<AbstractModel.Scenario, Integer>map(IterableExtensions.<AbstractModel.Scenario>filter(ownedScenarios, _function_4), _function_5));
          for (final Integer amount : clocksForState) {
            {
              final Function1<AbstractModel.Scenario, Boolean> _function_6 = (AbstractModel.Scenario sc_3) -> {
                return Boolean.valueOf((((sc_3.event().clock() != null) && (sc_3.event().clock().amount() == (amount).intValue())) && Objects.equals(this.getSourceState(sc_3.guard(), e.name()), s_1)));
              };
              final List<AbstractModel.Scenario> scenariosForClock = IterableExtensions.<AbstractModel.Scenario>toList(IterableExtensions.<AbstractModel.Scenario>filter(ownedScenarios, _function_6));
              final Function1<AbstractModel.Scenario, String> _function_7 = (AbstractModel.Scenario sc_3) -> {
                return this.buildGuardExpr(sc_3.guard(), e.name());
              };
              final Function1<String, Boolean> _function_8 = (String g) -> {
                boolean _isEmpty = g.isEmpty();
                return Boolean.valueOf((!_isEmpty));
              };
              final List<String> nonClockGuards = IterableExtensions.<String>toList(IterableExtensions.<String>filter(ListExtensions.<AbstractModel.Scenario, String>map(scenariosForClock, _function_7), _function_8));
              boolean _isEmpty = nonClockGuards.isEmpty();
              boolean _not = (!_isEmpty);
              if (_not) {
                String _xifexpression = null;
                int _size = nonClockGuards.size();
                boolean _equals = (_size == 1);
                if (_equals) {
                  String _head = IterableExtensions.<String>head(nonClockGuards);
                  String _plus = ("!((" + _head);
                  _xifexpression = (_plus + "))");
                } else {
                  String _join = IterableExtensions.join(nonClockGuards, ") || (");
                  String _plus_1 = ("!((" + _join);
                  _xifexpression = (_plus_1 + "))");
                }
                final String negated = _xifexpression;
                String _clockName = this.clockName((amount).intValue());
                String _plus_2 = (_clockName + " == ");
                String _plus_3 = (_plus_2 + amount);
                String _plus_4 = (_plus_3 + " && ");
                final String selfGuard = (_plus_4 + negated);
                String _clockName_1 = this.clockName((amount).intValue());
                final String selfUpdate = (_clockName_1 + " = 0");
                final String locId = this.locationIds.get(this.locKey(e.name(), s_1));
                edges.add(this.buildTransition(this.nextId(), locId, locId, null, selfGuard, null, selfUpdate));
              }
            }
          }
        }
      }
      for (final AbstractModel.Scenario sc_3 : onEndScenarios) {
        {
          final String e0 = this.ownerOf(sc_3);
          final String srcState = this.onEndState(sc_3);
          final String srcLocId = this.locationIds.get(this.locKey(e.name(), srcState));
          final String finLocId = this.locationIds.get(this.finishingLocKey(e.name(), sc_3));
          String _name = e.name();
          final boolean ownerIsE = Objects.equals(e0, _name);
          if (ownerIsE) {
            final String guardStr = this.buildGuard(sc_3.guard(), sc_3.event(), e.name());
            final String selectStr = this.buildSelect(sc_3.effect());
            edges.add(this.buildTransition(this.nextId(), srcLocId, finLocId, selectStr, guardStr, null, null));
          } else {
            String _nextId = this.nextId();
            String _name_1 = sc_3.name();
            String _plus = (_name_1 + "_trigger?");
            edges.add(this.buildTransition(_nextId, srcLocId, finLocId, null, null, _plus, null));
          }
          final Function1<AbstractModel.Scenario, Boolean> _function_4 = (AbstractModel.Scenario s2) -> {
            return Boolean.valueOf((Objects.equals(this.ownerOf(s2), e.name()) && Objects.equals(this.getSourceState(s2.guard(), e.name()), srcState)));
          };
          final List<AbstractModel.Scenario> exitingScenarios = IterableExtensions.<AbstractModel.Scenario>toList(IterableExtensions.<AbstractModel.Scenario>filter(m.scenarios(), _function_4));
          final Function1<AbstractModel.StateAssign, Boolean> _function_5 = (AbstractModel.StateAssign sa) -> {
            String _entity = sa.entity();
            String _name_2 = e.name();
            return Boolean.valueOf((!Objects.equals(_entity, _name_2)));
          };
          final List<AbstractModel.StateAssign> affectedNonE = IterableExtensions.<AbstractModel.StateAssign>toList(IterableExtensions.<AbstractModel.StateAssign>filter(this.collectStateAssigns(sc_3.effect()), _function_5));
          boolean _isEmpty = affectedNonE.isEmpty();
          final boolean needsChan = (!_isEmpty);
          for (final AbstractModel.Scenario sc2 : exitingScenarios) {
            {
              final AbstractModel.Effect mergedEffect = this.mergeEffects(sc2.effect(), sc_3.effect(), e.name());
              final Function1<AbstractModel.StateAssign, Boolean> _function_6 = (AbstractModel.StateAssign sa) -> {
                String _entity = sa.entity();
                String _name_2 = e.name();
                return Boolean.valueOf(Objects.equals(_entity, _name_2));
              };
              final AbstractModel.StateAssign primaryAssign = IterableExtensions.<AbstractModel.StateAssign>findFirst(this.collectStateAssigns(mergedEffect), _function_6);
              String _xifexpression = null;
              if ((primaryAssign != null)) {
                _xifexpression = primaryAssign.state();
              } else {
                _xifexpression = srcState;
              }
              final String targetState = _xifexpression;
              final String targetLocId = this.locationIds.get(this.locKey(e.name(), targetState));
              final String guardStr_1 = this.buildGuard(sc2.guard(), sc2.event(), e.name());
              final String updateStr = this.buildUpdate(mergedEffect, e.name(), allClockResets);
              final String selectStr_1 = this.buildSelect(mergedEffect);
              String _xifexpression_1 = null;
              if (needsChan) {
                String _name_2 = sc_3.name();
                _xifexpression_1 = (_name_2 + "!");
              } else {
                _xifexpression_1 = null;
              }
              final String syncStr = _xifexpression_1;
              edges.add(this.buildTransition(this.nextId(), finLocId, targetLocId, selectStr_1, guardStr_1, syncStr, updateStr));
            }
          }
          if (needsChan) {
            for (final AbstractModel.StateAssign sa : affectedNonE) {
              {
                final List<String> recvList = this.receiverEdges.get(sa.entity());
                if ((recvList != null)) {
                  this.addReceiverEdgesForEntity(sc_3, sa.entity(), recvList, m);
                }
              }
            }
          }
        }
      }
      final Function1<AbstractModel.Scenario, Boolean> _function_4 = (AbstractModel.Scenario sc_4) -> {
        return Boolean.valueOf((this.hasOnEnd(sc_4) && (!Objects.equals(this.onEndEntity(sc_4), e.name()))));
      };
      Iterable<AbstractModel.Scenario> _filter = IterableExtensions.<AbstractModel.Scenario>filter(ownedScenarios, _function_4);
      for (final AbstractModel.Scenario sc_4 : _filter) {
        {
          final String sourceState = this.getSourceState(sc_4.guard(), e.name());
          final String sourceLocId = this.locationIds.get(this.locKey(e.name(), sourceState));
          final String guardStr = this.buildGuard(sc_4.guard(), sc_4.event(), e.name());
          String _nextId = this.nextId();
          String _name = sc_4.name();
          String _plus = (_name + "_trigger!");
          edges.add(this.buildTransition(_nextId, sourceLocId, sourceLocId, null, guardStr, _plus, allClockResets));
        }
      }
      edges.addAll(this.receiverEdges.get(e.name()));
      String _xifexpression = null;
      boolean _isEmpty = clockValues.isEmpty();
      if (_isEmpty) {
        _xifexpression = "clock c_0;";
      } else {
        final Function1<Integer, String> _function_5 = (Integer v) -> {
          String _clockName = this.clockName((v).intValue());
          String _plus = ("clock " + _clockName);
          return (_plus + ";");
        };
        _xifexpression = IterableExtensions.join(IterableExtensions.<Integer, String>map(clockValues, _function_5), " ");
      }
      final String clockDecl = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("\t");
      _builder.append("<template>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<name>");
      String _name = e.name();
      _builder.append(_name, "\t\t");
      _builder.append("</name>");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("<declaration>");
      _builder.append(clockDecl, "\t\t");
      _builder.append("</declaration>");
      _builder.newLineIfNotEmpty();
      String _join = IterableExtensions.join(locations, "\n");
      _builder.append(_join);
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("<init ref=\"");
      _builder.append(initLocId, "\t\t");
      _builder.append("\"/>");
      _builder.newLineIfNotEmpty();
      String _join_1 = IterableExtensions.join(edges, "\n");
      _builder.append(_join_1);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("</template>");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  private void addReceiverEdgesForEntity(final AbstractModel.Scenario sc, final String entityName, final List<String> out, final AbstractModel.Model m) {
    final Function1<AbstractModel.Entity, Boolean> _function = (AbstractModel.Entity e) -> {
      String _name = e.name();
      return Boolean.valueOf(Objects.equals(_name, entityName));
    };
    final AbstractModel.Entity entity = IterableExtensions.<AbstractModel.Entity>findFirst(m.entities(), _function);
    final AbstractModel.StatesProperty statesProp = IterableExtensions.<AbstractModel.StatesProperty>head(Iterables.<AbstractModel.StatesProperty>filter(entity.properties(), AbstractModel.StatesProperty.class));
    final Function1<AbstractModel.StateAssign, Boolean> _function_1 = (AbstractModel.StateAssign s) -> {
      String _entity = s.entity();
      return Boolean.valueOf(Objects.equals(_entity, entityName));
    };
    final AbstractModel.StateAssign sa = IterableExtensions.<AbstractModel.StateAssign>findFirst(this.collectStateAssigns(sc.effect()), _function_1);
    if ((sa == null)) {
      return;
    }
    final String targetLocId = this.locationIds.get(this.locKey(entityName, sa.state()));
    final Function1<AbstractModel.Scenario, Boolean> _function_2 = (AbstractModel.Scenario s) -> {
      return Boolean.valueOf(((Objects.equals(this.ownerOf(s), entityName) && (s.event().clock() != null)) && (s.event().clock().amount() > 0)));
    };
    final Function1<AbstractModel.Scenario, Integer> _function_3 = (AbstractModel.Scenario s) -> {
      return Integer.valueOf(s.event().clock().amount());
    };
    final Set<Integer> receiverClockValues = IterableExtensions.<Integer>toSet(IterableExtensions.<AbstractModel.Scenario, Integer>map(IterableExtensions.<AbstractModel.Scenario>filter(m.scenarios(), _function_2), _function_3));
    final Function1<Integer, String> _function_4 = (Integer v) -> {
      String _clockName = this.clockName((v).intValue());
      return (_clockName + " = 0");
    };
    final String clockResets = IterableExtensions.join(IterableExtensions.<Integer, String>map(receiverClockValues, _function_4), ", ");
    String _stateVar = this.stateVar(entityName);
    String _plus = (_stateVar + " = ");
    String _stateConst = this.stateConst(entityName, sa.state());
    String _plus_1 = (_plus + _stateConst);
    String _xifexpression = null;
    boolean _isEmpty = clockResets.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      _xifexpression = (", " + clockResets);
    } else {
      _xifexpression = "";
    }
    final String updateStr = (_plus_1 + _xifexpression);
    List<String> _stateNames = statesProp.stateNames();
    for (final String s : _stateNames) {
      {
        final String fromLocId = this.locationIds.get(this.locKey(entityName, s));
        String _nextId = this.nextId();
        String _name = sc.name();
        String _plus_2 = (_name + "?");
        out.add(this.buildTransition(_nextId, fromLocId, targetLocId, null, null, _plus_2, updateStr));
      }
    }
  }

  private AbstractModel.Effect mergeEffects(final AbstractModel.Effect sc2Effect, final AbstractModel.Effect scEffect, final String entityName) {
    AbstractModel.Effect _xblockexpression = null;
    {
      final List<AbstractModel.Effect> sc2Parts = this.flattenEffect(sc2Effect);
      final List<AbstractModel.Effect> scParts = this.flattenEffect(scEffect);
      final Function1<AbstractModel.PropAssign, String> _function = (AbstractModel.PropAssign it) -> {
        return it.ref().prop();
      };
      final Set<String> scProps = IterableExtensions.<String>toSet(IterableExtensions.<AbstractModel.PropAssign, String>map(Iterables.<AbstractModel.PropAssign>filter(scParts, AbstractModel.PropAssign.class), _function));
      final Function1<AbstractModel.PropDelta, String> _function_1 = (AbstractModel.PropDelta it) -> {
        return it.ref().prop();
      };
      final Set<String> scDeltas = IterableExtensions.<String>toSet(IterableExtensions.<AbstractModel.PropDelta, String>map(Iterables.<AbstractModel.PropDelta>filter(scParts, AbstractModel.PropDelta.class), _function_1));
      final Function1<AbstractModel.StateAssign, Boolean> _function_2 = (AbstractModel.StateAssign it) -> {
        String _entity = it.entity();
        return Boolean.valueOf(Objects.equals(_entity, entityName));
      };
      final boolean scHasStateAssign = IterableExtensions.<AbstractModel.StateAssign>exists(Iterables.<AbstractModel.StateAssign>filter(scParts, AbstractModel.StateAssign.class), _function_2);
      final ArrayList<AbstractModel.Effect> kept = new ArrayList<AbstractModel.Effect>();
      for (final AbstractModel.Effect part : sc2Parts) {
        boolean _matched = false;
        if (part instanceof AbstractModel.StateAssign) {
          _matched=true;
          if ((Objects.equals(((AbstractModel.StateAssign)part).entity(), entityName) && scHasStateAssign)) {
          } else {
            kept.add(part);
          }
        }
        if (!_matched) {
          if (part instanceof AbstractModel.PropAssign) {
            _matched=true;
            boolean _contains = scProps.contains(((AbstractModel.PropAssign)part).ref().prop());
            if (_contains) {
            } else {
              kept.add(part);
            }
          }
        }
        if (!_matched) {
          if (part instanceof AbstractModel.PropDelta) {
            _matched=true;
            boolean _contains = scDeltas.contains(((AbstractModel.PropDelta)part).ref().prop());
            if (_contains) {
            } else {
              kept.add(part);
            }
          }
        }
        if (!_matched) {
          kept.add(part);
        }
      }
      for (final AbstractModel.Effect part_1 : scParts) {
        kept.add(part_1);
      }
      final Function2<AbstractModel.Effect, AbstractModel.Effect, AbstractModel.Effect> _function_3 = (AbstractModel.Effect acc, AbstractModel.Effect f) -> {
        return new AbstractModel.SequenceEffect(acc, f);
      };
      _xblockexpression = IterableExtensions.<AbstractModel.Effect>reduce(kept, _function_3);
    }
    return _xblockexpression;
  }

  private List<AbstractModel.Effect> flattenEffect(final AbstractModel.Effect e) {
    ArrayList<AbstractModel.Effect> _xblockexpression = null;
    {
      final ArrayList<AbstractModel.Effect> result = new ArrayList<AbstractModel.Effect>();
      boolean _matched = false;
      if (e instanceof AbstractModel.SequenceEffect) {
        _matched=true;
        result.addAll(this.flattenEffect(((AbstractModel.SequenceEffect)e).left()));
        result.addAll(this.flattenEffect(((AbstractModel.SequenceEffect)e).right()));
      }
      if (!_matched) {
        result.add(e);
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }

  private AbstractModel.Effect collectEffectsForEntity(final AbstractModel.Effect e, final String entityName) {
    AbstractModel.Effect _xblockexpression = null;
    {
      final Function1<AbstractModel.Effect, Boolean> _function = (AbstractModel.Effect part) -> {
        boolean _switchResult = false;
        boolean _matched = false;
        if (part instanceof AbstractModel.StateAssign) {
          _matched=true;
          String _entity = ((AbstractModel.StateAssign)part).entity();
          _switchResult = Objects.equals(_entity, entityName);
        }
        if (!_matched) {
          if (part instanceof AbstractModel.PropAssign) {
            _matched=true;
            String _entity = ((AbstractModel.PropAssign)part).ref().entity();
            _switchResult = Objects.equals(_entity, entityName);
          }
        }
        if (!_matched) {
          if (part instanceof AbstractModel.PropDelta) {
            _matched=true;
            String _entity = ((AbstractModel.PropDelta)part).ref().entity();
            _switchResult = Objects.equals(_entity, entityName);
          }
        }
        if (!_matched) {
          if (part instanceof AbstractModel.PropRandom) {
            _matched=true;
            String _entity = ((AbstractModel.PropRandom)part).ref().entity();
            _switchResult = Objects.equals(_entity, entityName);
          }
        }
        if (!_matched) {
          _switchResult = false;
        }
        return Boolean.valueOf(_switchResult);
      };
      final List<AbstractModel.Effect> parts = IterableExtensions.<AbstractModel.Effect>toList(IterableExtensions.<AbstractModel.Effect>filter(this.flattenEffect(e), _function));
      AbstractModel.Effect _xifexpression = null;
      boolean _isEmpty = parts.isEmpty();
      if (_isEmpty) {
        _xifexpression = null;
      } else {
        final Function2<AbstractModel.Effect, AbstractModel.Effect, AbstractModel.Effect> _function_1 = (AbstractModel.Effect acc, AbstractModel.Effect f) -> {
          return new AbstractModel.SequenceEffect(acc, f);
        };
        _xifexpression = IterableExtensions.<AbstractModel.Effect>reduce(parts, _function_1);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  private String buildLocation(final String locId, final String name, final int x, final boolean urgent, final String invariant) {
    String _xblockexpression = null;
    {
      final StringBuilder sb = new StringBuilder();
      sb.append((((("\t\t<location id=\"" + locId) + "\" x=\"") + Integer.valueOf(x)) + "\" y=\"0\">\n"));
      sb.append((((("\t\t\t<name x=\"" + Integer.valueOf((x - 10))) + "\" y=\"-30\">") + name) + "</name>\n"));
      if (urgent) {
        sb.append("\t\t\t<committed/>\n");
      } else {
        if (((invariant != null) && (!invariant.isEmpty()))) {
          String _xmlEscape = this.xmlEscape(invariant);
          String _plus = ((("\t\t\t<label kind=\"invariant\" x=\"" + Integer.valueOf((x - 10))) + "\" y=\"15\">") + _xmlEscape);
          String _plus_1 = (_plus + "</label>\n");
          sb.append(_plus_1);
        }
      }
      sb.append("\t\t</location>");
      _xblockexpression = sb.toString();
    }
    return _xblockexpression;
  }

  private String buildTransition(final String edgeId, final String sourceId, final String targetId, final String selectStr, final String guardStr, final String syncStr, final String updateStr) {
    String _xblockexpression = null;
    {
      final StringBuilder sb = new StringBuilder();
      sb.append((("\t\t<transition id=\"" + edgeId) + "\">\n"));
      sb.append((("\t\t\t<source ref=\"" + sourceId) + "\"/>\n"));
      sb.append((("\t\t\t<target ref=\"" + targetId) + "\"/>\n"));
      if (((selectStr != null) && (!selectStr.isEmpty()))) {
        sb.append((("\t\t\t<label kind=\"select\" x=\"0\" y=\"-60\">" + selectStr) + "</label>\n"));
      }
      if (((guardStr != null) && (!guardStr.isEmpty()))) {
        String _xmlEscape = this.xmlEscape(guardStr);
        String _plus = ("\t\t\t<label kind=\"guard\" x=\"0\" y=\"-40\">" + _xmlEscape);
        String _plus_1 = (_plus + "</label>\n");
        sb.append(_plus_1);
      }
      if (((syncStr != null) && (!syncStr.isEmpty()))) {
        sb.append((("\t\t\t<label kind=\"synchronisation\" x=\"0\" y=\"-20\">" + syncStr) + "</label>\n"));
      }
      if (((updateStr != null) && (!updateStr.isEmpty()))) {
        String _xmlEscape_1 = this.xmlEscape(updateStr);
        String _plus_2 = ("\t\t\t<label kind=\"assignment\" x=\"0\" y=\"0\">" + _xmlEscape_1);
        String _plus_3 = (_plus_2 + "</label>\n");
        sb.append(_plus_3);
      }
      sb.append("\t\t</transition>");
      _xblockexpression = sb.toString();
    }
    return _xblockexpression;
  }

  private String buildGuard(final AbstractModel.Guard g, final AbstractModel.Event ev, final String e0) {
    String _xblockexpression = null;
    {
      final ArrayList<String> parts = new ArrayList<String>();
      if (((ev.clock() != null) && (ev.clock().amount() > 0))) {
        String _clockName = this.clockName(ev.clock().amount());
        String _plus = (_clockName + " == ");
        int _amount = ev.clock().amount();
        String _plus_1 = (_plus + Integer.valueOf(_amount));
        parts.add(_plus_1);
      }
      final String stateGuard = this.buildGuardExpr(g, e0);
      boolean _isEmpty = stateGuard.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        parts.add(stateGuard);
      }
      _xblockexpression = IterableExtensions.join(parts, " && ");
    }
    return _xblockexpression;
  }

  private String buildGuardExpr(final AbstractModel.Guard g, final String e0) {
    String _switchResult = null;
    boolean _matched = false;
    if (g instanceof AbstractModel.StateEquality) {
      _matched=true;
      String _xifexpression = null;
      String _entity = ((AbstractModel.StateEquality)g).entity();
      boolean _equals = Objects.equals(_entity, e0);
      if (_equals) {
        _xifexpression = "";
      } else {
        String _stateVar = this.stateVar(((AbstractModel.StateEquality)g).entity());
        String _plus = (_stateVar + " == ");
        String _stateConst = this.stateConst(((AbstractModel.StateEquality)g).entity(), ((AbstractModel.StateEquality)g).state());
        _xifexpression = (_plus + _stateConst);
      }
      _switchResult = _xifexpression;
    }
    if (!_matched) {
      if (g instanceof AbstractModel.StateInequality) {
        _matched=true;
        String _stateVar = this.stateVar(((AbstractModel.StateInequality)g).entity());
        String _plus = (_stateVar + " != ");
        String _stateConst = this.stateConst(((AbstractModel.StateInequality)g).entity(), ((AbstractModel.StateInequality)g).state());
        _switchResult = (_plus + _stateConst);
      }
    }
    if (!_matched) {
      if (g instanceof AbstractModel.PropComparison) {
        _matched=true;
        String _propVar = this.propVar(((AbstractModel.PropComparison)g).ref().entity(), ((AbstractModel.PropComparison)g).ref().prop());
        String _plus = (_propVar + " ");
        String _compOpSymbol = this.compOpSymbol(((AbstractModel.PropComparison)g).op());
        String _plus_1 = (_plus + _compOpSymbol);
        String _plus_2 = (_plus_1 + " ");
        String _buildValueExpr = this.buildValueExpr(((AbstractModel.PropComparison)g).value());
        _switchResult = (_plus_2 + _buildValueExpr);
      }
    }
    if (!_matched) {
      if (g instanceof AbstractModel.BetweenGuard) {
        _matched=true;
        String _propVar = this.propVar(((AbstractModel.BetweenGuard)g).ref().entity(), ((AbstractModel.BetweenGuard)g).ref().prop());
        String _plus = (_propVar + " >= ");
        String _buildValueExpr = this.buildValueExpr(((AbstractModel.BetweenGuard)g).low());
        String _plus_1 = (_plus + _buildValueExpr);
        String _plus_2 = (_plus_1 + " && ");
        String _propVar_1 = this.propVar(((AbstractModel.BetweenGuard)g).ref().entity(), ((AbstractModel.BetweenGuard)g).ref().prop());
        String _plus_3 = (_plus_2 + _propVar_1);
        String _plus_4 = (_plus_3 + " <= ");
        String _buildValueExpr_1 = this.buildValueExpr(((AbstractModel.BetweenGuard)g).high());
        _switchResult = (_plus_4 + _buildValueExpr_1);
      }
    }
    if (!_matched) {
      if (g instanceof AbstractModel.ConjunctionGuard) {
        _matched=true;
        String _xblockexpression = null;
        {
          final String l = this.buildGuardExpr(((AbstractModel.ConjunctionGuard)g).left(), e0);
          final String r = this.buildGuardExpr(((AbstractModel.ConjunctionGuard)g).right(), e0);
          String _xifexpression = null;
          boolean _isEmpty = l.isEmpty();
          if (_isEmpty) {
            _xifexpression = r;
          } else {
            String _xifexpression_1 = null;
            boolean _isEmpty_1 = r.isEmpty();
            if (_isEmpty_1) {
              _xifexpression_1 = l;
            } else {
              _xifexpression_1 = ((l + " && ") + r);
            }
            _xifexpression = _xifexpression_1;
          }
          _xblockexpression = _xifexpression;
        }
        _switchResult = _xblockexpression;
      }
    }
    return _switchResult;
  }

  private String buildUpdate(final AbstractModel.Effect e, final String entityName, final String allClockResets) {
    String _xblockexpression = null;
    {
      final List<String> parts = this.collectUpdateParts(e, entityName);
      boolean _isEmpty = allClockResets.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        parts.add(allClockResets);
      }
      _xblockexpression = IterableExtensions.join(parts, ", ");
    }
    return _xblockexpression;
  }

  private List<String> collectUpdateParts(final AbstractModel.Effect e, final String entityName) {
    ArrayList<String> _xblockexpression = null;
    {
      final ArrayList<String> parts = new ArrayList<String>();
      boolean _matched = false;
      if (e instanceof AbstractModel.StateAssign) {
        _matched=true;
        String _entity = ((AbstractModel.StateAssign)e).entity();
        boolean _equals = Objects.equals(_entity, entityName);
        if (_equals) {
          String _stateVar = this.stateVar(((AbstractModel.StateAssign)e).entity());
          String _plus = (_stateVar + " = ");
          String _stateConst = this.stateConst(((AbstractModel.StateAssign)e).entity(), ((AbstractModel.StateAssign)e).state());
          String _plus_1 = (_plus + _stateConst);
          parts.add(_plus_1);
        }
      }
      if (!_matched) {
        if (e instanceof AbstractModel.PropAssign) {
          _matched=true;
          String _propVar = this.propVar(((AbstractModel.PropAssign)e).ref().entity(), ((AbstractModel.PropAssign)e).ref().prop());
          String _plus = (_propVar + " = ");
          String _buildValueExpr = this.buildValueExpr(((AbstractModel.PropAssign)e).value());
          String _plus_1 = (_plus + _buildValueExpr);
          parts.add(_plus_1);
        }
      }
      if (!_matched) {
        if (e instanceof AbstractModel.PropDelta) {
          _matched=true;
          String _xifexpression = null;
          AbstractModel.DeltaOp _op = ((AbstractModel.PropDelta)e).op();
          boolean _equals = Objects.equals(_op, AbstractModel.DeltaOp.INC);
          if (_equals) {
            _xifexpression = "+=";
          } else {
            _xifexpression = "-=";
          }
          final String op = _xifexpression;
          String _propVar = this.propVar(((AbstractModel.PropDelta)e).ref().entity(), ((AbstractModel.PropDelta)e).ref().prop());
          String _plus = (_propVar + " ");
          String _plus_1 = (_plus + op);
          String _plus_2 = (_plus_1 + " ");
          String _buildValueExpr = this.buildValueExpr(((AbstractModel.PropDelta)e).value());
          String _plus_3 = (_plus_2 + _buildValueExpr);
          parts.add(_plus_3);
        }
      }
      if (!_matched) {
        if (e instanceof AbstractModel.PropRandom) {
          _matched=true;
          String _propVar = this.propVar(((AbstractModel.PropRandom)e).ref().entity(), ((AbstractModel.PropRandom)e).ref().prop());
          String _plus = (_propVar + " = i");
          parts.add(_plus);
        }
      }
      if (!_matched) {
        if (e instanceof AbstractModel.SequenceEffect) {
          _matched=true;
          parts.addAll(this.collectUpdateParts(((AbstractModel.SequenceEffect)e).left(), entityName));
          parts.addAll(this.collectUpdateParts(((AbstractModel.SequenceEffect)e).right(), entityName));
        }
      }
      _xblockexpression = parts;
    }
    return _xblockexpression;
  }

  private String buildSelect(final AbstractModel.Effect e) {
    String _xblockexpression = null;
    {
      final AbstractModel.PropRandom random = this.findPropRandom(e);
      if ((random == null)) {
        return null;
      }
      String _buildValueExpr = this.buildValueExpr(random.low());
      String _plus = ("i : int[" + _buildValueExpr);
      String _plus_1 = (_plus + ", ");
      String _buildValueExpr_1 = this.buildValueExpr(random.high());
      String _plus_2 = (_plus_1 + _buildValueExpr_1);
      _xblockexpression = (_plus_2 + "]");
    }
    return _xblockexpression;
  }

  private AbstractModel.PropRandom findPropRandom(final AbstractModel.Effect e) {
    AbstractModel.PropRandom _switchResult = null;
    boolean _matched = false;
    if (e instanceof AbstractModel.PropRandom) {
      _matched=true;
      _switchResult = ((AbstractModel.PropRandom)e);
    }
    if (!_matched) {
      if (e instanceof AbstractModel.SequenceEffect) {
        _matched=true;
        AbstractModel.PropRandom _xblockexpression = null;
        {
          final AbstractModel.PropRandom l = this.findPropRandom(((AbstractModel.SequenceEffect)e).left());
          AbstractModel.PropRandom _xifexpression = null;
          if ((l != null)) {
            _xifexpression = l;
          } else {
            _xifexpression = this.findPropRandom(((AbstractModel.SequenceEffect)e).right());
          }
          _xblockexpression = _xifexpression;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }

  private List<AbstractModel.StateAssign> collectStateAssigns(final AbstractModel.Effect e) {
    ArrayList<AbstractModel.StateAssign> _xblockexpression = null;
    {
      final ArrayList<AbstractModel.StateAssign> result = new ArrayList<AbstractModel.StateAssign>();
      boolean _matched = false;
      if (e instanceof AbstractModel.StateAssign) {
        _matched=true;
        result.add(((AbstractModel.StateAssign)e));
      }
      if (!_matched) {
        if (e instanceof AbstractModel.SequenceEffect) {
          _matched=true;
          result.addAll(this.collectStateAssigns(((AbstractModel.SequenceEffect)e).left()));
          result.addAll(this.collectStateAssigns(((AbstractModel.SequenceEffect)e).right()));
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }

  private String getSourceState(final AbstractModel.Guard g, final String entityName) {
    String _switchResult = null;
    boolean _matched = false;
    if (g instanceof AbstractModel.StateEquality) {
      _matched=true;
      String _xifexpression = null;
      String _entity = ((AbstractModel.StateEquality)g).entity();
      boolean _equals = Objects.equals(_entity, entityName);
      if (_equals) {
        _xifexpression = ((AbstractModel.StateEquality)g).state();
      } else {
        _xifexpression = null;
      }
      _switchResult = _xifexpression;
    }
    if (!_matched) {
      if (g instanceof AbstractModel.ConjunctionGuard) {
        _matched=true;
        String _xblockexpression = null;
        {
          final String l = this.getSourceState(((AbstractModel.ConjunctionGuard)g).left(), entityName);
          String _xifexpression = null;
          if ((l != null)) {
            _xifexpression = l;
          } else {
            _xifexpression = this.getSourceState(((AbstractModel.ConjunctionGuard)g).right(), entityName);
          }
          _xblockexpression = _xifexpression;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }

  private boolean isUrgentLocation(final String entityName, final String state, final List<AbstractModel.Scenario> scenarios) {
    final Function1<AbstractModel.Scenario, Boolean> _function = (AbstractModel.Scenario s) -> {
      return Boolean.valueOf((((s.event().clock() != null) && (s.event().clock().amount() == 0)) && Objects.equals(this.getSourceState(s.guard(), entityName), state)));
    };
    return IterableExtensions.<AbstractModel.Scenario>exists(scenarios, _function);
  }

  private String getLocationInvariant(final String entityName, final String state, final List<AbstractModel.Scenario> scenarios) {
    String _xblockexpression = null;
    {
      final Function1<AbstractModel.Scenario, Boolean> _function = (AbstractModel.Scenario s) -> {
        return Boolean.valueOf((((s.event().clock() != null) && (s.event().clock().amount() > 0)) && Objects.equals(this.getSourceState(s.guard(), entityName), state)));
      };
      final Function1<AbstractModel.Scenario, Integer> _function_1 = (AbstractModel.Scenario s) -> {
        return Integer.valueOf(s.event().clock().amount());
      };
      final Set<Integer> amounts = IterableExtensions.<Integer>toSet(IterableExtensions.<AbstractModel.Scenario, Integer>map(IterableExtensions.<AbstractModel.Scenario>filter(scenarios, _function), _function_1));
      boolean _isEmpty = amounts.isEmpty();
      if (_isEmpty) {
        return null;
      }
      final Function1<Integer, String> _function_2 = (Integer v) -> {
        String _clockName = this.clockName((v).intValue());
        String _plus = (_clockName + " <= ");
        return (_plus + v);
      };
      _xblockexpression = IterableExtensions.join(IterableExtensions.<Integer, String>map(amounts, _function_2), " && ");
    }
    return _xblockexpression;
  }

  private String ownerOf(final AbstractModel.Scenario sc) {
    return this.ownerOfGuard(sc.guard());
  }

  private String ownerOfGuard(final AbstractModel.Guard g) {
    String _switchResult = null;
    boolean _matched = false;
    if (g instanceof AbstractModel.StateEquality) {
      _matched=true;
      _switchResult = ((AbstractModel.StateEquality)g).entity();
    }
    if (!_matched) {
      if (g instanceof AbstractModel.ConjunctionGuard) {
        _matched=true;
        String _elvis = null;
        String _ownerOfGuard = this.ownerOfGuard(((AbstractModel.ConjunctionGuard)g).left());
        if (_ownerOfGuard != null) {
          _elvis = _ownerOfGuard;
        } else {
          String _ownerOfGuard_1 = this.ownerOfGuard(((AbstractModel.ConjunctionGuard)g).right());
          _elvis = _ownerOfGuard_1;
        }
        _switchResult = _elvis;
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }

  private boolean hasOnEnd(final AbstractModel.Scenario sc) {
    AbstractModel.OnEnd _onEnd = sc.event().onEnd();
    return (_onEnd != null);
  }

  private String onEndEntity(final AbstractModel.Scenario sc) {
    return sc.event().onEnd().entity();
  }

  private String onEndState(final AbstractModel.Scenario sc) {
    return sc.event().onEnd().state();
  }

  private String buildValueExpr(final AbstractModel.Value v) {
    String _switchResult = null;
    boolean _matched = false;
    if (v instanceof AbstractModel.NumberValue) {
      _matched=true;
      _switchResult = String.valueOf(((AbstractModel.NumberValue)v).value());
    }
    if (!_matched) {
      if (v instanceof AbstractModel.PropValue) {
        _matched=true;
        _switchResult = this.propVar(((AbstractModel.PropValue)v).ref().entity(), ((AbstractModel.PropValue)v).ref().prop());
      }
    }
    return _switchResult;
  }

  private String compOpSymbol(final AbstractModel.CompOp op) {
    String _switchResult = null;
    if (op != null) {
      switch (op) {
        case EQ:
          _switchResult = "==";
          break;
        case NEQ:
          _switchResult = "!=";
          break;
        case GT:
          _switchResult = ">";
          break;
        case LT:
          _switchResult = "<";
          break;
        case GEQ:
          _switchResult = ">=";
          break;
        case LEQ:
          _switchResult = "<=";
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }

  private String clockName(final int amount) {
    return ("c_" + Integer.valueOf(amount));
  }

  private String locKey(final String entity, final String state) {
    return ((entity + ".") + state);
  }

  private String finishingLocKey(final String entity, final AbstractModel.Scenario sc) {
    String _onEndState = this.onEndState(sc);
    String _plus = ((entity + ".") + _onEndState);
    String _plus_1 = (_plus + "_finishing_");
    String _name = sc.name();
    return (_plus_1 + _name);
  }

  private String stateConst(final String entity, final String state) {
    return ((entity + "_") + state).toUpperCase();
  }

  private String stateVar(final String entity) {
    return (entity + "_state");
  }

  private String propVar(final String entity, final String prop) {
    return ((entity + "_") + prop);
  }

  private String nextId() {
    int _plusPlus = this.idCounter++;
    return ("id" + Integer.valueOf(_plusPlus));
  }

  private String generateQueries(final AbstractModel.Model m) {
    final Function1<AbstractModel.Requirement, String> _function = (AbstractModel.Requirement it) -> {
      return this.generateQuery(it);
    };
    return IterableExtensions.join(ListExtensions.<AbstractModel.Requirement, String>map(m.requirements(), _function), "\n");
  }

  private String generateQuery(final AbstractModel.Requirement r) {
    String _xblockexpression = null;
    {
      final String formula = this.generateFormula(r);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("\t\t");
      _builder.append("<query>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<formula>");
      String _xmlEscape = this.xmlEscape(formula);
      _builder.append(_xmlEscape, "\t\t\t");
      _builder.append("</formula>");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.append("<comment>");
      String _name = r.name();
      _builder.append(_name, "\t\t\t");
      _builder.append("</comment>");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("</query>");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  private String generateFormula(final AbstractModel.Requirement r) {
    String _xblockexpression = null;
    {
      final AbstractModel.TimeBound tb = r.timeBound();
      String _switchResult = null;
      AbstractModel.Check _check = r.check();
      boolean _matched = false;
      if (_check instanceof AbstractModel.CheckQuery) {
        _matched=true;
        String _xblockexpression_1 = null;
        {
          AbstractModel.Check _check_1 = r.check();
          final AbstractModel.CheckQuery c = ((AbstractModel.CheckQuery) _check_1);
          final String sf = this.buildGuardExpr(c.cond(), "");
          String _xifexpression = null;
          boolean _diamond = c.diamond();
          if (_diamond) {
            _xifexpression = "<>";
          } else {
            _xifexpression = "[]";
          }
          final String op = _xifexpression;
          String _xifexpression_1 = null;
          boolean _existential = c.existential();
          if (_existential) {
            String _xifexpression_2 = null;
            if ((tb != null)) {
              int _amount = tb.amount();
              String _plus = ("Pr[<=" + Integer.valueOf(_amount));
              String _plus_1 = (_plus + "] (");
              String _plus_2 = (_plus_1 + op);
              String _plus_3 = (_plus_2 + " ");
              String _plus_4 = (_plus_3 + sf);
              _xifexpression_2 = (_plus_4 + ")");
            } else {
              _xifexpression_2 = (((("E" + op) + " (") + sf) + ")");
            }
            _xifexpression_1 = _xifexpression_2;
          } else {
            _xifexpression_1 = (((("A" + op) + " (") + sf) + ")");
          }
          _xblockexpression_1 = _xifexpression_1;
        }
        _switchResult = _xblockexpression_1;
      }
      if (!_matched) {
        if (_check instanceof AbstractModel.NeverCheck) {
          _matched=true;
          String _xblockexpression_1 = null;
          {
            AbstractModel.Check _check_1 = r.check();
            final AbstractModel.NeverCheck c = ((AbstractModel.NeverCheck) _check_1);
            String _buildGuardExpr = this.buildGuardExpr(c.cond(), "");
            String _plus = ("A[] not (" + _buildGuardExpr);
            _xblockexpression_1 = (_plus + ")");
          }
          _switchResult = _xblockexpression_1;
        }
      }
      if (!_matched) {
        if (_check instanceof AbstractModel.MaxCheck) {
          _matched=true;
          String _xblockexpression_1 = null;
          {
            AbstractModel.Check _check_1 = r.check();
            final AbstractModel.MaxCheck c = ((AbstractModel.MaxCheck) _check_1);
            int _amount = tb.amount();
            String _plus = ("E[<=" + Integer.valueOf(_amount));
            String _plus_1 = (_plus + "; 100] (max: ");
            String _propVar = this.propVar(c.ref().entity(), c.ref().prop());
            String _plus_2 = (_plus_1 + _propVar);
            _xblockexpression_1 = (_plus_2 + ")");
          }
          _switchResult = _xblockexpression_1;
        }
      }
      if (!_matched) {
        if (_check instanceof AbstractModel.MinCheck) {
          _matched=true;
          String _xblockexpression_1 = null;
          {
            AbstractModel.Check _check_1 = r.check();
            final AbstractModel.MinCheck c = ((AbstractModel.MinCheck) _check_1);
            int _amount = tb.amount();
            String _plus = ("E[<=" + Integer.valueOf(_amount));
            String _plus_1 = (_plus + "; 100] (min: ");
            String _propVar = this.propVar(c.ref().entity(), c.ref().prop());
            String _plus_2 = (_plus_1 + _propVar);
            _xblockexpression_1 = (_plus_2 + ")");
          }
          _switchResult = _xblockexpression_1;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }

  private String xmlEscape(final String s) {
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
  }
}
