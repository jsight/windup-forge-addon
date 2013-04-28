package org.jboss.windup.ui;

import javax.inject.Inject;

import org.jboss.forge.container.addons.AddonRegistry;
import org.jboss.forge.resource.DirectoryResource;
import org.jboss.forge.ui.UICommand;
import org.jboss.forge.ui.context.UIBuilder;
import org.jboss.forge.ui.context.UIContext;
import org.jboss.forge.ui.context.UIValidationContext;
import org.jboss.forge.ui.input.UIInput;
import org.jboss.forge.ui.input.UIInputMany;
import org.jboss.forge.ui.metadata.UICommandMetadata;
import org.jboss.forge.ui.metadata.WithAttributes;
import org.jboss.forge.ui.result.Result;
import org.jboss.forge.ui.result.Results;
import org.jboss.forge.ui.util.Categories;
import org.jboss.forge.ui.util.Metadata;
import org.jboss.windup.impl.WindupMain;

public class WindupWizard implements UICommand
{
   @Inject
   private AddonRegistry registry;

   @Inject
   @WithAttributes(label = "Input:", required = true)
   private UIInput<DirectoryResource> input;

   @Inject
   @WithAttributes(label = "Output:", required = true)
   private UIInput<DirectoryResource> output;

   @Inject
   @WithAttributes(label = "Scan Java Packages:", required = true)
   private UIInputMany<String> packages;

   @Override
   public UICommandMetadata getMetadata()
   {
      return Metadata.forCommand(getClass()).name("Run Windup").description("Run Windup Migation Analyzer")
               .category(Categories.create("Platform", "Migration"));
   }

   @Override
   public boolean isEnabled(UIContext context)
   {
      return true;
   }

   @Override
   public void initializeUI(final UIBuilder builder) throws Exception
   {
      builder.add(input).add(output).add(packages);
   }

   @Override
   public void validate(UIValidationContext context)
   {
   }

   @Override
   public Result execute(UIContext context) throws Exception
   {
      StringBuilder builder = new StringBuilder();
      for (String packg : packages.getValue())
      {
         builder.append(packg).append(" ");
      }
      try
      {
         WindupMain.main(new String[] { "-input", input.getValue().getFullyQualifiedName(), "-output",
                  output.getValue().getFullyQualifiedName(), "-javaPkgs", builder.toString() });
         return Results.success();
      }
      catch (Exception e)
      {
         return Results.fail("Could not run Windup", e);
      }
   }

}
