package com.capgemini;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AppLifecycleBean {

  @Inject
  LeakCpuService leakCpuService;
  private static final Logger LOGGER = Logger.getLogger("ListenerBean");

  void onStart(@Observes StartupEvent ev) {
    LOGGER.info("The application is starting...");
  }

  void onStop(@Observes ShutdownEvent ev) {
    LOGGER.info("The application is stopping...");
//    leakCpuService.leakCpu();
  }

}