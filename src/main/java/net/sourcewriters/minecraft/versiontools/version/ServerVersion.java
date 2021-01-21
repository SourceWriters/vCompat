package net.sourcewriters.minecraft.versiontools.version;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;
import com.syntaxphoenix.syntaxapi.version.Version;
import com.syntaxphoenix.syntaxapi.version.VersionAnalyzer;
import com.syntaxphoenix.syntaxapi.version.VersionFormatter;

public class ServerVersion extends Version {

     public static final ServerAnalyzer ANALYZER = new ServerAnalyzer();

     protected int revision;

     public ServerVersion() {
          this(0);
     }

     public ServerVersion(int major) {
          this(major, 0);
     }

     public ServerVersion(int major, int minor) {
          this(major, minor, 0);
     }

     public ServerVersion(int major, int minor, int patch) {
          this(major, minor, patch, 0);
     }

     public ServerVersion(int major, int minor, int patch, int revision) {
          super(major, minor, patch);
          this.revision = revision;
     }

     public int getRevision() {
          return revision;
     }

     @Override
     protected ServerVersion setMajor(int major) {
          super.setMajor(major);
          return this;
     }

     @Override
     protected ServerVersion setMinor(int minor) {
          super.setMinor(minor);
          return this;
     }

     @Override
     protected ServerVersion setPatch(int patch) {
          super.setPatch(patch);
          return this;
     }

     protected ServerVersion setRevision(int revision) {
          this.revision = revision;
          return this;
     }

     @Override
     public boolean isHigher(Version version) {
          if (super.isHigher(version)) {
               return true;
          }
          if (version instanceof ServerVersion) {
               ServerVersion other = (ServerVersion) version;
               return revision > other.revision;
          } else {
               return revision > 0;
          }
     }

     @Override
     public boolean isSimilar(Version version) {
          return super.isSimilar(version) && ((version instanceof ServerVersion) && revision == ((ServerVersion) version).revision);
     }

     @Override
     public boolean isLower(Version version) {
          if (super.isHigher(version)) {
               return true;
          }
          if (version instanceof ServerVersion) {
               ServerVersion other = (ServerVersion) version;
               return revision < other.revision;
          }
          return false;
     }

     @Override
     public ServerVersion clone() {
          return new ServerVersion(getMajor(), getMinor(), getPatch(), revision);
     }

     @Override
     public ServerVersion update(int major, int minor, int patch) {
          return update(major, minor, patch, 0);
     }

     public ServerVersion update(int major, int minor, int patch, int revision) {
          return ((ServerVersion) super.update(major, minor, patch)).setRevision(this.revision + revision);
     }

     @Override
     protected ServerVersion init(int major, int minor, int patch) {
          return new ServerVersion(major, minor, patch);
     }

     @Override
     public ServerAnalyzer getAnalyzer() {
          return ANALYZER;
     }

     @Override
     public VersionFormatter getFormatter() {
          return version -> {
               StringBuilder builder = new StringBuilder();
               builder.append('v');
               builder.append(version.getMajor());
               builder.append('_');
               builder.append(version.getMinor());
               builder.append("_R");
               builder.append(version.getPatch());

               if (version instanceof ServerVersion) {
                    ServerVersion server = (ServerVersion) version;
                    if (server.getRevision() != 0) {
                         builder.append('.');
                         builder.append(server.getRevision());
                    }
               }

               return builder.toString();
          };
     }

     public static class ServerAnalyzer implements VersionAnalyzer {
          @Override
          public ServerVersion analyze(String formatted) {
               ServerVersion version = new ServerVersion();
               String[] parts = (formatted = formatted.replaceFirst("v", "")).contains("_") ? formatted.split("_")
                    : (formatted.contains(".") ? formatted.split("\\.")
                         : new String[] {
                              formatted
                         });
               try {
                    if (parts.length == 1) {
                         version.setMajor(Strings.isNumeric(parts[0]) ? Integer.parseInt(parts[0]) : 0);
                    } else if (parts.length == 2) {
                         version.setMajor(Strings.isNumeric(parts[0]) ? Integer.parseInt(parts[0]) : 0);
                         version.setMinor(Strings.isNumeric(parts[1]) ? Integer.parseInt(parts[1]) : 0);
                    } else if (parts.length >= 3) {
                         version.setMajor(Strings.isNumeric(parts[0]) ? Integer.parseInt(parts[0]) : 0);
                         version.setMinor(Strings.isNumeric(parts[1]) ? Integer.parseInt(parts[1]) : 0);
                         if ((parts[2] = parts[2].replaceFirst("R", "")).contains(".")) {
                              String[] parts0 = parts[2].split("\\.");
                              version.setPatch(Strings.isNumeric(parts0[0]) ? Integer.parseInt(parts0[0]) : 0);
                              version.setRevision(Strings.isNumeric(parts0[1]) ? Integer.parseInt(parts0[1]) : 0);
                         } else {
                              version.setPatch(Strings.isNumeric(parts[2]) ? Integer.parseInt(parts[2]) : 0);
                         }
                    }
               } catch (NumberFormatException ex) {

               }
               return version;
          }
     }

}