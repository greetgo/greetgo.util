package kz.greetgo.teamcity.soundir.controller;

import kz.greetgo.teamcity.soundir.configs.BuildType;

public interface Finisher {
  void finish(BuildType buildType);
}
