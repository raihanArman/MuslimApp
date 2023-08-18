package com.raydev.domain.di

import com.raydev.domain.usecase.prayer.*
import com.raydev.domain.usecase.quran.GetAyahBySurahIdUseCase
import com.raydev.domain.usecase.quran.GetSurahAyahUseCase
import com.raydev.domain.usecase.quran.GetSurahUseCase
import com.raydev.domain.usecase.quran.SetupQuranUseCase
import org.koin.dsl.module

object UseCaseModule {
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
    }
}