package meteor.plugins.zulrah.rotationutils;

import javax.annotation.Nonnull;
import net.runelite.api.NPC;
import meteor.plugins.zulrah.constants.ZulrahLocation;
import meteor.plugins.zulrah.constants.ZulrahType;

public final class ZulrahNpc {
   @Nonnull
   private final ZulrahType type;
   @Nonnull
   private final ZulrahLocation zulrahLocation;
   private final boolean jad;

   public static ZulrahNpc valueOf(NPC zulrah, boolean jad) {
      return new ZulrahNpc(ZulrahType.valueOf(zulrah.getId()), ZulrahLocation.valueOf(zulrah.getLocalLocation()), jad);
   }

   public ZulrahNpc(@Nonnull ZulrahType type, @Nonnull ZulrahLocation zulrahLocation, boolean jad) {
      if (type == null) {
         throw new NullPointerException("type is marked non-null but is null");
      } else if (zulrahLocation == null) {
         throw new NullPointerException("zulrahLocation is marked non-null but is null");
      } else {
         this.type = type;
         this.zulrahLocation = zulrahLocation;
         this.jad = jad;
      }
   }

   @Nonnull
   public ZulrahType getType() {
      return this.type;
   }

   @Nonnull
   public ZulrahLocation getZulrahLocation() {
      return this.zulrahLocation;
   }

   public boolean isJad() {
      return this.jad;
   }

   public String toString() {
      ZulrahType var10000 = this.getType();
      return "ZulrahNpc(type=" + var10000 + ", zulrahLocation=" + this.getZulrahLocation() + ", jad=" + this.isJad() + ")";
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ZulrahNpc)) {
         return false;
      } else {
         ZulrahNpc other = (ZulrahNpc)o;
         Object this$type = this.getType();
         Object other$type = other.getType();
         if (this$type == null) {
            if (other$type != null) {
               return false;
            }
         } else if (!this$type.equals(other$type)) {
            return false;
         }

         label29: {
            Object this$zulrahLocation = this.getZulrahLocation();
            Object other$zulrahLocation = other.getZulrahLocation();
            if (this$zulrahLocation == null) {
               if (other$zulrahLocation == null) {
                  break label29;
               }
            } else if (this$zulrahLocation.equals(other$zulrahLocation)) {
               break label29;
            }

            return false;
         }

         if (this.isJad() != other.isJad()) {
            return false;
         } else {
            return true;
         }
      }
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $type = this.getType();
      result = result * PRIME + ($type == null ? 43 : $type.hashCode());
      Object $zulrahLocation = this.getZulrahLocation();
      result = result * PRIME + ($zulrahLocation == null ? 43 : $zulrahLocation.hashCode());
      result = result * PRIME + (this.isJad() ? 79 : 97);
      return result;
   }
}
