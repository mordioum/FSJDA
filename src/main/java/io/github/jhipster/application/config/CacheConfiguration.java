package io.github.jhipster.application.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Discipline.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Discipline.class.getName() + ".ligues", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Ligue.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Ligue.class.getName() + ".dojoclubs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DojoClub.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DojoClub.class.getName() + ".athletes", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Athlete.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Athlete.class.getName() + ".licences", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TarifCeinture.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TarifCeinture.class.getName() + ".licences", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Licence.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Saison.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Saison.class.getName() + ".licences", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
