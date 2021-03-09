package net.astrocube.tnt.run.game;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.game.*;
import net.astrocube.tnt.perk.CachedPerkHandler;
import net.astrocube.tnt.perk.CoreCachedPerkHandler;
import net.astrocube.tnt.perk.PerkProvider;

import javax.inject.Named;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new CommonGameModule());
        bind(ScoreboardProvider.class).to(CoreScoreboardProvider.class);
        bind(PerkProvider.class).annotatedWith(Names.named("doubleJump")).to(CoreDoubleJumpProvider.class);
    }

    @Provides @Named("doubleJump") @Singleton
    public CachedPerkHandler createDoubleJumpHandler(@Named("doubleJump") PerkProvider provider) {
        return new CoreCachedPerkHandler(provider);
    }

}
