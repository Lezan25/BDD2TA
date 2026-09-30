package dk.sdu.generator

import dk.sdu.bdd.Model
import dk.sdu.translation.AbstractModelPrinter
import dk.sdu.translation.ConcreteToAbstract
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext

class BddGenerator extends AbstractGenerator {

    override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
        if (resource.contents.isEmpty) return
        val model = resource.contents.head as Model
        if (model === null) return

        val abstract = new ConcreteToAbstract().translateModel(model)
        System.out.println(new AbstractModelPrinter().print(abstract))
        //val xml = new TranslateAbstractToUppaal().generate(abstract)
        //fsa.generateFile("output.xml", xml)
    }
}