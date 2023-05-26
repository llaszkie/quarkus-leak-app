package com.capgemini;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LeakCpuService {
  public void leakCpu() {
    // Consume CPU indefinitely
    while (true) {
      // Perform some CPU-intensive task
      for (int i = 0; i < 1000000; i++) {
        Math.sqrt(i);
      }
    }
  }
}
