package osrs;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kw")
public class class304 {
   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "1682823323"
   )
   static int method5528(int var0, Script var1, boolean var2) {
      int var3;
      if (var0 == 3903) {
         var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].type();
         return 1;
      } else if (var0 == 3904) {
         var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].id;
         return 1;
      } else if (var0 == 3905) {
         var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].unitPrice;
         return 1;
      } else if (var0 == 3906) {
         var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].totalQuantity;
         return 1;
      } else if (var0 == 3907) {
         var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].currentQuantity;
         return 1;
      } else if (var0 == 3908) {
         var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].currentPrice;
         return 1;
      } else {
         int var4;
         if (var0 == 3910) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            var4 = Client.grandExchangeOffers[var3].status();
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var4 == 0 ? 1 : 0;
            return 1;
         } else if (var0 == 3911) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            var4 = Client.grandExchangeOffers[var3].status();
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var4 == 2 ? 1 : 0;
            return 1;
         } else if (var0 == 3912) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            var4 = Client.grandExchangeOffers[var3].status();
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var4 == 5 ? 1 : 0;
            return 1;
         } else if (var0 == 3913) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            var4 = Client.grandExchangeOffers[var3].status();
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var4 == 1 ? 1 : 0;
            return 1;
         } else {
            boolean var5;
            if (var0 == 3914) {
               var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               if (BuddyRankComparator.grandExchangeEvents != null) {
                  BuddyRankComparator.grandExchangeEvents.sort(GrandExchangeEvents.GrandExchangeEvents_nameComparator, var5);
               }

               return 1;
            } else if (var0 == 3915) {
               var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               if (BuddyRankComparator.grandExchangeEvents != null) {
                  BuddyRankComparator.grandExchangeEvents.sort(GrandExchangeEvents.GrandExchangeEvents_priceComparator, var5);
               }

               return 1;
            } else if (var0 == 3916) {
               class44.Interpreter_intStackSize -= 2;
               var5 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] == 1;
               boolean var13 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1] == 1;
               if (BuddyRankComparator.grandExchangeEvents != null) {
                  Client.GrandExchangeEvents_worldComparator.filterWorlds = var13;
                  BuddyRankComparator.grandExchangeEvents.sort(Client.GrandExchangeEvents_worldComparator, var5);
               }

               return 1;
            } else if (var0 == 3917) {
               var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               if (BuddyRankComparator.grandExchangeEvents != null) {
                  BuddyRankComparator.grandExchangeEvents.sort(GrandExchangeEvents.GrandExchangeEvents_ageComparator, var5);
               }

               return 1;
            } else if (var0 == 3918) {
               var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               if (BuddyRankComparator.grandExchangeEvents != null) {
                  BuddyRankComparator.grandExchangeEvents.sort(GrandExchangeEvents.GrandExchangeEvents_quantityComparator, var5);
               }

               return 1;
            } else if (var0 == 3919) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = BuddyRankComparator.grandExchangeEvents == null ? 0 : BuddyRankComparator.grandExchangeEvents.events.size();
               return 1;
            } else {
               GrandExchangeEvent var6;
               if (var0 == 3920) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = (GrandExchangeEvent)BuddyRankComparator.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.world;
                  return 1;
               } else if (var0 == 3921) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = (GrandExchangeEvent)BuddyRankComparator.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var6.getOfferName();
                  return 1;
               } else if (var0 == 3922) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = (GrandExchangeEvent)BuddyRankComparator.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var6.getPreviousOfferName();
                  return 1;
               } else if (var0 == 3923) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = (GrandExchangeEvent)BuddyRankComparator.grandExchangeEvents.events.get(var3);
                  long var7 = ObjectComposition.currentTimeMillis() - Widget.field3099 - var6.age;
                  int var9 = (int)(var7 / 3600000L);
                  int var10 = (int)((var7 - (long)(var9 * 3600000)) / 60000L);
                  int var11 = (int)((var7 - (long)(var9 * 3600000) - (long)(var10 * '\uea60')) / 1000L);
                  String var12 = var9 + ":" + var10 / 10 + var10 % 10 + ":" + var11 / 10 + var11 % 10;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var12;
                  return 1;
               } else if (var0 == 3924) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = (GrandExchangeEvent)BuddyRankComparator.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.grandExchangeOffer.totalQuantity;
                  return 1;
               } else if (var0 == 3925) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = (GrandExchangeEvent)BuddyRankComparator.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.grandExchangeOffer.unitPrice;
                  return 1;
               } else if (var0 == 3926) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = (GrandExchangeEvent)BuddyRankComparator.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.grandExchangeOffer.id;
                  return 1;
               } else {
                  return 2;
               }
            }
         }
      }
   }
}
