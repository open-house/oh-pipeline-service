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
curl -X PUT -H "Content-Type: application/xml" --data "&lt;phase&gt;&lt;uri&gt;http://some-url&lt;/uri&gt;&lt;/phase&lt;" http://localhost:8000/projects/&lt;project&gt;/versions/&lt;version&gt;/phases/&lt;phase&gt;
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

