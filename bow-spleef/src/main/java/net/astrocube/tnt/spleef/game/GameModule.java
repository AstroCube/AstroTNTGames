package net.astrocube.tnt.spleef.game;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.tnt.game.CommonGameModule;
import net.astrocube.tnt.perk.CachedPerkHandler;
import net.astrocube.tnt.perk.CoreCachedPerkHandler;
import net.astrocube.tnt.perk.PerkProvider;
import net.astrocube.tnt.game.ScoreboardProvider;

import javax.inject.Named;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new CommonGameModule());
        bind(ScoreboardProvider.class).to(CoreScoreboardProvider.class);
        bind(PerkProvider.class).annotatedWith(Names.named("doubleJump")).to(CoreDoubleJumpProvider.class);
        bind(PerkProvider.class).annotatedWith(Names.named("tripleShot")).to(CoreTripleShotProvider.class);
    }

    @Provides @Named("doubleJump") @Singleton
    public CachedPerkHandler provideDoubleJump(@Named("doubleJump") PerkProvider perkProvider) {
        return new CoreCachedPerkHandler(perkProvider);
    }

    @Provides @Named("tripleShot") @Singleton
    public CachedPerkHandler provideTripleShot(@Named("tripleShot") PerkProvider perkProvider) {
        return new CoreCachedPerkHandler(perkProvider);
    }

}
