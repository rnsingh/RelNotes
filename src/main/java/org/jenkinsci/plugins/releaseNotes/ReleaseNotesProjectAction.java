package org.jenkinsci.plugins.releaseNotes;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 */
public class ReleaseNotesProjectAction implements Action {

    private AbstractProject<?, ?> project;

    @Override
    public String getIconFileName() {
        return "/plugin/releaseNotes/img/project_icon.png";
    }

    @Override
    public String getDisplayName() {
        return "Release Notes Project Action";
    }

    @Override
    public String getUrlName() {
        return "releaseNotesPA";
    }

    public AbstractProject<?, ?> getProject() {
        return this.project;
    }

    public String getProjectName() {
        return this.project.getName();
    }

    public List<String> getProjectMessages() {
        List<String> projectMessages = new ArrayList<String>();
        List<? extends AbstractBuild<?, ?>> builds = project.getBuilds();
        String projectMessage="";
        final Class<ReleaseNotesBuildAction> buildClass = ReleaseNotesBuildAction.class;

        for (AbstractBuild<?, ?> currentBuild : builds) {
            projectMessage = "Build #"+currentBuild.getAction(buildClass).getBuildNumber()
                    +": "+currentBuild.getAction(buildClass).getMessage();
            projectMessages.add(projectMessage);
        }
        return projectMessages;
    }

    ReleaseNotesProjectAction(final AbstractProject<?, ?> project) {
        this.project = project;
    }
}
