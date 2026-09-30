package dk.sdu.scoping

import dk.sdu.bdd.EndEvent
import dk.sdu.bdd.PropRef
import dk.sdu.bdd.StateCondition
import dk.sdu.bdd.StateEffect
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.scoping.Scopes
import dk.sdu.bdd.BddPackage

class BddScopeProvider extends AbstractBddScopeProvider {

    override getScope(EObject context, EReference reference) {

        if (context instanceof StateCondition) {
            if (reference == BddPackage.Literals.STATE_CONDITION__STATE) {
                val entity = context.entity
                if (entity !== null)
                    return Scopes.scopeFor(entity.states)
            }
        }

        if (context instanceof StateEffect) {
            if (reference == BddPackage.Literals.STATE_EFFECT__STATE) {
                val entity = context.entity
                if (entity !== null)
                    return Scopes.scopeFor(entity.states)
            }
        }

        if (context instanceof EndEvent) {
            if (reference == BddPackage.Literals.END_EVENT__STATE) {
                val entity = context.entity
                if (entity !== null)
                    return Scopes.scopeFor(entity.states)
            }
        }

        if (context instanceof PropRef) {
            if (reference == BddPackage.Literals.PROP_REF__PROP) {
                val entity = context.entity
                if (entity !== null)
                    return Scopes.scopeFor(entity.properties)
            }
        }

        return super.getScope(context, reference)
    }
}