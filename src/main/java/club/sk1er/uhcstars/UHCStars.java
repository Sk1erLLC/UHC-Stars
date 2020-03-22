package club.sk1er.uhcstars;

import club.sk1er.modcore.ModCoreInstaller;
import club.sk1er.uhcstars.command.StarsCommand;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "Sk1er-UHCstars", name = "UHC Stars", version = "2.1")
public class UHCStars {

  @EventHandler
  public void init(FMLInitializationEvent event) {
    ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
    ClientCommandHandler.instance.registerCommand(new StarsCommand());
  }
}
