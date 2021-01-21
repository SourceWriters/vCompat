package net.sourcewriters.minecraft.versiontools.reflection;

public abstract class VersionHandler<V extends VersionControl> {

     protected final V versionControl;

     protected VersionHandler(V versionControl) {
          this.versionControl = versionControl;
     }

     public final V getVersionControl() {
          return versionControl;
     }

}
