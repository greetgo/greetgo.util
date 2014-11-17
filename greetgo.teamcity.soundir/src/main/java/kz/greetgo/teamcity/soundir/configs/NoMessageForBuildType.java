package kz.greetgo.teamcity.soundir.configs;

public class NoMessageForBuildType extends RuntimeException {
  public final BuildType buildType;
  
  public NoMessageForBuildType(BuildType buildType) {
    super("buildType = " + buildType);
    this.buildType = buildType;
  }
}
