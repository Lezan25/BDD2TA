package dk.sdu.generator;

import dk.sdu.bdd.Model;
import dk.sdu.model.AbstractModel;
import dk.sdu.translation.AbstractModelPrinter;
import dk.sdu.translation.ConcreteToAbstract;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class BddGenerator extends AbstractGenerator {
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    boolean _isEmpty = resource.getContents().isEmpty();
    if (_isEmpty) {
      return;
    }
    EObject _head = IterableExtensions.<EObject>head(resource.getContents());
    final Model model = ((Model) _head);
    if ((model == null)) {
      return;
    }
    final AbstractModel.Model abstract_ = new ConcreteToAbstract().translateModel(model);
    System.out.println(new AbstractModelPrinter().print(abstract_));
  }
}
