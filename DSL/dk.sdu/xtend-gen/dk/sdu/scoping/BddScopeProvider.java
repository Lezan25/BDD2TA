package dk.sdu.scoping;

import dk.sdu.bdd.BddPackage;
import dk.sdu.bdd.EndEvent;
import dk.sdu.bdd.Entity;
import dk.sdu.bdd.PropRef;
import dk.sdu.bdd.StateCondition;
import dk.sdu.bdd.StateEffect;
import java.util.Objects;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

@SuppressWarnings("all")
public class BddScopeProvider extends AbstractBddScopeProvider {
  @Override
  public IScope getScope(final EObject context, final EReference reference) {
    if ((context instanceof StateCondition)) {
      boolean _equals = Objects.equals(reference, BddPackage.Literals.STATE_CONDITION__STATE);
      if (_equals) {
        final Entity entity = ((StateCondition)context).getEntity();
        if ((entity != null)) {
          return Scopes.scopeFor(entity.getStates());
        }
      }
    }
    if ((context instanceof StateEffect)) {
      boolean _equals_1 = Objects.equals(reference, BddPackage.Literals.STATE_EFFECT__STATE);
      if (_equals_1) {
        final Entity entity_1 = ((StateEffect)context).getEntity();
        if ((entity_1 != null)) {
          return Scopes.scopeFor(entity_1.getStates());
        }
      }
    }
    if ((context instanceof EndEvent)) {
      boolean _equals_2 = Objects.equals(reference, BddPackage.Literals.END_EVENT__STATE);
      if (_equals_2) {
        final Entity entity_2 = ((EndEvent)context).getEntity();
        if ((entity_2 != null)) {
          return Scopes.scopeFor(entity_2.getStates());
        }
      }
    }
    if ((context instanceof PropRef)) {
      boolean _equals_3 = Objects.equals(reference, BddPackage.Literals.PROP_REF__PROP);
      if (_equals_3) {
        final Entity entity_3 = ((PropRef)context).getEntity();
        if ((entity_3 != null)) {
          return Scopes.scopeFor(entity_3.getProperties());
        }
      }
    }
    return super.getScope(context, reference);
  }
}
