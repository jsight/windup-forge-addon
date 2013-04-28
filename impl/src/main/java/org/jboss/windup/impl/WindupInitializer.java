package org.jboss.windup.impl;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.forge.container.Forge;
import org.jboss.forge.container.event.PostStartup;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class WindupInitializer
{

   @Inject
   private Forge forge;

   public void run(@Observes PostStartup event)
   {
      String[] args = forge.getArgs();
      if (args != null && args.length > 0)
         WindupMain.main(args);
   }
}
