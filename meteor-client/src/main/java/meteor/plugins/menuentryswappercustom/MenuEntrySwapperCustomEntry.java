package meteor.plugins.menuentryswappercustom;

import java.util.Objects;

class MenuEntrySwapperCustomEntry {

  private String option;
  private String target;
  private String topOption;
  private String topTarget;

  MenuEntrySwapperCustomEntry(String option, String target) {
    this(option, target, null, null);
  }

  public boolean equals(Object other) {
    if (!(other instanceof MenuEntrySwapperCustomEntry)) {
      return false;
    } else {
      return this.option.equals(((MenuEntrySwapperCustomEntry) other).option) && this.target
          .equals(((MenuEntrySwapperCustomEntry) other).target) && this.topOption
          .equals(((MenuEntrySwapperCustomEntry) other).topOption) && this.topTarget
          .equals(((MenuEntrySwapperCustomEntry) other).topTarget);
    }
  }

  public int hashCode() {
    return Objects.hash(this.option, this.target, this.topOption, this.topTarget);
  }

  public String getOption() {
    return this.option;
  }

  public String getTarget() {
    return this.target;
  }

  public String getTopOption() {
    return this.topOption;
  }

  public String getTopTarget() {
    return this.topTarget;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setTopOption(String topOption) {
    this.topOption = topOption;
  }

  public void setTopTarget(String topTarget) {
    this.topTarget = topTarget;
  }

  public String toString() {
    String var10000 = this.getOption();
    return "zMenuEntryPlugin.MenuEntrySwapperCustomEntry(option=" + var10000 + ", target=" + this
        .getTarget() + ", topOption=" + this.getTopOption() + ", topTarget=" + this.getTopTarget()
        + ")";
  }

  public MenuEntrySwapperCustomEntry(String option, String target, String topOption,
      String topTarget) {
    this.option = option;
    this.target = target;
    this.topOption = topOption;
    this.topTarget = topTarget;
  }
}
