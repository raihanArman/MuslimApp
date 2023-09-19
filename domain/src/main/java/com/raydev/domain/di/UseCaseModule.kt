package com.raydev.domain.di

import com.raydev.domain.usecase.GetNearbyMosqueUseCase
import com.raydev.domain.usecase.prayer.GetAsharDataUseCase
import com.raydev.domain.usecase.prayer.GetCurrentPrayerTimeUseCase
import com.raydev.domain.usecase.prayer.GetDhuhurDataUseCase
import com.raydev.domain.usecase.prayer.GetImsakDataUseCase
import com.raydev.domain.usecase.prayer.GetIsyaDataUseCase
import com.raydev.domain.usecase.prayer.GetMaghribDataUseCase
import com.raydev.domain.usecase.prayer.GetPrayerTimeUseCase
import com.raydev.domain.usecase.prayer.GetSholatTimeUseCase
import com.raydev.domain.usecase.prayer.GetSubuhDataUseCase
import com.raydev.domain.usecase.prayer.SearchCityUseCase
import com.raydev.domain.usecase.prayer.SetAsharDataUseCase
import com.raydev.domain.usecase.prayer.SetDhuhurDataUseCase
import com.raydev.domain.usecase.prayer.SetImsakDataUseCase
import com.raydev.domain.usecase.prayer.SetIsyaDataUseCase
import com.raydev.domain.usecase.prayer.SetMaghribDataUseCase
import com.raydev.domain.usecase.prayer.SetSubuhDataUseCase
import com.raydev.domain.usecase.quran.BookmarkAyahUseCase
import com.raydev.domain.usecase.quran.GetAyahBySurahIdUseCase
import com.raydev.domain.usecase.quran.GetSurahAyahUseCase
import com.raydev.domain.usecase.quran.GetSurahUseCase
import com.raydev.domain.usecase.quran.SetupQuranUseCase
import org.koin.dsl.module

var useCaseModule = module {
    factory {
        GetSurahUseCase(get())
    }

    factory {
        GetAyahBySurahIdUseCase(get())
    }

    factory {
        SearchCityUseCase(get())
    }

    factory {
        GetSholatTimeUseCase(get())
    }

    factory {
        SetImsakDataUseCase(get())
    }

    factory {
        SetSubuhDataUseCase(get())
    }

    factory {
        SetDhuhurDataUseCase(get())
    }

    factory {
        SetAsharDataUseCase(get())
    }

    factory {
        SetMaghribDataUseCase(get())
    }

    factory {
        SetIsyaDataUseCase(get())
    }

    factory {
        GetImsakDataUseCase(get())
    }

    factory {
        GetSubuhDataUseCase(get())
    }

    factory {
        GetDhuhurDataUseCase(get())
    }

    factory {
        GetAsharDataUseCase(get())
    }

    factory {
        GetMaghribDataUseCase(get())
    }

    factory {
        GetIsyaDataUseCase(get())
    }

    factory {
        SetupQuranUseCase(get())
    }

    factory {
        GetSurahAyahUseCase(get())
    }

    factory {
        GetCurrentPrayerTimeUseCase(get())
    }

    factory {
        GetPrayerTimeUseCase(get())
    }

    factory {
        BookmarkAyahUseCase(get())
    }

    factory {
        GetNearbyMosqueUseCase(get())
    }
}
