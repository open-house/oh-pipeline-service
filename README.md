oh-pipeline-service
===================

description
-----------

Continuous delivery/deployment pipeline REST service.
This service allows to add (and run) different phases after the build 
is created (usually this step is done in CI server).

Pipeline Structure:
<pre>
/projects
    /&lt;project&gt;
        /versions
            /&lt;version&gt;
                /phases
                    /&lt;phase&gt;
                /builds
                    /&lt;build&gt;
                        /phases
                            /&lt;phase&gt;
</pre>

Before adding a new build to the pipeline you need to set up project and
version for which you will be adding builds.
<pre><code>
# add new project
curl -X PUT http://localhost:8000/projects/&lt;project&gt;
# add new version
curl -X PUT http://localhost:8000/projects/&lt;project&gt;/versions/&lt;version&gt;
</code></pre>

Now you can add new build to the pipeline, but at this state nothing will 
happen. You need to add phases and URL for every phase. The URL will be 
called when the build goes through the specific phase.

<pre><code>
# add new phase
curl -X PUT -H "Content-Type: application/xml" --data "&lt;phase&gt;&lt;uri&gt;http://some-url&lt;/uri&gt;&lt;/phase&gt;" http://localhost:8000/projects/&lt;project&gt;/versions/&lt;version&gt;/phases/&lt;phase&gt;
</code></pre>

Now you can add new build
<pre><code>
# add new phase
curl -X PUT http://localhost:8000/projects/&lt;project&gt;/versions/&lt;version&gt;/builds/&lt;build&gt;
</code></pre>

After this, the state of the first phase will be set to IN_PROGRESS, then the
specified URL will get called. If the call fails, the FAIL state will be 
added to that phase. If the call is successful, then it is up to the called
service to update the phase with either FAIL or SUCCESS state

<pre><code>
# add new state (updating phase)
curl -X POST -H "Content-Type: application/xml" --data "&lt;state&gt;&lt;name&gt;SUCCESS&lt;/name&gt;&lt;/state&gt;" http://localhost:8000/projects/&lt;project&gt;/versions/&lt;version&gt;/builds/&lt;build&gt;/phases/&lt;phase&gt;
</code></pre>

depending on the state, pipeline will either move to the next phase (SUCCESS)
or stops (FAIL)

Failed phase can be re-run by updating its state to IN_PROGRESS, or skipped
by updating to SUCCESS.

running application
-------------------

Create database <code>pipeline_service</code>

    mysql -u root -p
    CREATE DATABASE pipeline_service;

Create database user <code>pipeline_service</code> with the same password

    CREATE USER 'pipeline_service'@'localhost' IDENTIFIED BY PASSWORD 'pipeline_service';
    GRANT ALL on pipeline_service.* to pipeline_service@localhost;

Run <code>mvn jetty:run</code>

Rpplication will be running on port 8000 <b>localhost:8000</b>

creating debian package
-----------------------

run <code>./build &lt;build_number&gt;</code> this will generate debian
package that can be installed.

Application is installed to <code>/opt/oh-pipeline-service</code>
and is started (db has to be setup) by 
<code>/etc/init.d/oh-pipeline-service start</code>. Started app. runs on 
the port 8000

application configuration
-------------------------

After package is installed, configuration is located in
<code>/opt/oh-pipeline-service/webapps/WEB-INF/classes/</code>

<code>log4j.xml</code> - logging configuration

<code>oh-pipeline-service-jdbc.properties</code> - database configuration

<code>oh-pipeline-service.properties</code> - application configuration

tomcat configuration
--------------------

tomcat configuration is located in
<code>/etc/default/oh-pipeline-service</code>

To enable monitoring, add this line to the file above:
<code>CATALINA_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.hostname=&lt;your_host&gt;"</code>

And you can use jConsole or VisualVM to monitor application.
