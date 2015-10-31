package voiddecay.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import voiddecay.EntityVoidFireball;
import voiddecay.blocks.BlockCleanseTNT;
import voiddecay.blocks.BlockVoidDecay;
import voiddecay.blocks.BlockVoidTNT;
import voiddecay.blocks.EntityCleanseTNT;
import voiddecay.blocks.EntityVoidTNT;
import voiddecay.core.proxies.CommonProxy;
import voiddecay.handlers.ConfigHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = VoidDecay.MODID, version = VoidDecay.VERSION, name = VoidDecay.NAME, guiFactory = "voiddecay.handlers.ConfigGuiFactory")
public class VoidDecay
{
    public static final String MODID = "voiddecay";
    public static final String VERSION = "VD_VER_KEY";
    public static final String NAME = "VoidDecay";
    public static final String PROXY = "voiddecay.core.proxies";
    public static final String CHANNEL = "VOID_DECAY_CHAN";
	
	@Instance(MODID)
	public static VoidDecay instance;
	
	@SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
	
	public static Block decay;
	public static Block voidTNT;
	public static Block cleanseTNT;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
    	ConfigHandler.config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	ConfigHandler.initConfigs();
    	
    	proxy.registerHandlers();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	EntityRegistry.registerModEntity(EntityVoidFireball.class, "void_fireball", EntityRegistry.findGlobalUniqueEntityId(), instance, 128, 15, true);
    	
    	decay = new BlockVoidDecay();
    	voidTNT = new BlockVoidTNT();
    	cleanseTNT = new BlockCleanseTNT();
    	GameRegistry.registerBlock(decay, "void_decay");
    	GameRegistry.registerBlock(voidTNT, "void_tnt");
    	GameRegistry.registerBlock(cleanseTNT, "cleanse_tnt");
    	
    	GameRegistry.addShapedRecipe(new ItemStack(voidTNT), "TST", "SNS", "TST", 'T', new ItemStack(Blocks.tnt), 'S', new ItemStack(Items.skull, 1, 1), 'N', new ItemStack(Items.nether_star));
    	GameRegistry.addShapedRecipe(new ItemStack(cleanseTNT), "TST", "SNS", "TST", 'T', new ItemStack(Blocks.tnt), 'S', new ItemStack(Items.potionitem, 1, 8229), 'N', new ItemStack(Items.golden_apple, 1, 1));
    	
    	int vTntID = EntityRegistry.findGlobalUniqueEntityId();
    	EntityRegistry.registerGlobalEntityID(EntityVoidTNT.class, "void_tnt", vTntID);
    	EntityRegistry.registerModEntity(EntityVoidTNT.class, "void_tnt", vTntID, this, 64, 10, true);
    	
    	int cTntID = EntityRegistry.findGlobalUniqueEntityId();
    	EntityRegistry.registerGlobalEntityID(EntityCleanseTNT.class, "cleanse_tnt", cTntID);
    	EntityRegistry.registerModEntity(EntityCleanseTNT.class, "cleanse_tnt", cTntID, this, 64, 10, true);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
