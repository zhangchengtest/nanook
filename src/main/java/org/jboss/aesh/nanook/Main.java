/*
 * JBoss, Home of Professional Open Source Copyright 2014 Red Hat Inc. and/or its affiliates and
 * other contributors as indicated by the @authors tag. All rights reserved. See the copyright.txt
 * in the distribution for a full listing of individual contributors.
 * 
 * Licensed under the Apache License, Version 2.0 (the 'License') you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an 'AS IS' BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.jboss.aesh.nanook;

import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.container.DefaultWarDeployment;
import org.wildfly.swarm.container.WarDeployment;
import org.wildfly.swarm.logging.LoggingFraction;

/**
 * @author <a href='mailto:00hf11@gmail.com'>Helio Frota</a>
 */
public class Main {

    public static void main(String... args) throws Exception {

        Container container = new Container();
        
        container.subsystem(new LoggingFraction()
        .formatter("PATTERN", "%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n")
        .consoleHandler("DEBUG", "PATTERN")
        .rootLogger("CONSOLE", "DEBUG"));
        
        WarDeployment deployment = new DefaultWarDeployment(container);
        deployment.staticContent("/");
        deployment.getArchive().addClass(AeshHandler.class);
        deployment.getArchive().addClass(NanookServlet.class);
        container.start().deploy(deployment);
    }
}
