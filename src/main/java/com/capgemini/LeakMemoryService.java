package com.capgemini;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class LeakMemoryService {

  private static List<Byte[]> leakRef = new LinkedList<>();
  public void leakMemory() {
    // Allocate memory indefinitely
    while (true) {
      Byte[] memory = new Byte[1024 * 1024]; // 1MB
      leakRef.add(memory);
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}