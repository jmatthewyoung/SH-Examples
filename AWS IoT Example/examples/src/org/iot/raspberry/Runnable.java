package org.iot.raspberry;

import org.iot.raspberry.grovepi.GrovePi;

public interface Runnable {

  public void run(GrovePi grovePi, Monitor monitor) throws Exception;

}
