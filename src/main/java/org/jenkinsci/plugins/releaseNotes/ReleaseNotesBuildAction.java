package org.jenkinsci.plugins.releaseNotes;

import hudson.model.AbstractBuild;
import hudson.model.Action;

/**
 * 
 */
public class ReleaseNotesBuildAction implements Action {

    private String message;
    private AbstractBuild<?, ?> build;

    @Override
    public String getIconFileName() {
        return "/plugin/ReleaseNotes/img/build-goals.png";
    }

    @Override
    public String getDisplayName() {
        return "Release Notes Build Page";
    }

    @Override
    public String getUrlName() {
        return "releaseNotesBA";
    }

    public String getMessage() {
        return this.message;
    }

    public int getBuildNumber() {
        return this.build.number;
    }

    public AbstractBuild<?, ?> getBuild() {
        return build;
    }

    ReleaseNotesBuildAction(final String message, final AbstractBuild<?, ?> build)
    {
        this.message = message;
        this.build = build;
    }
}
