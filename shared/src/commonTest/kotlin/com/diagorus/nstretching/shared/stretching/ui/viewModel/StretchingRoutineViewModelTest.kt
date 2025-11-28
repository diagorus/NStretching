package com.diagorus.nstretching.shared.stretching.ui.viewModel

import com.diagorus.nstretching.shared.stretching.data.routine.StretchingRoutineRepository
import com.diagorus.nstretching.shared.util.audio.BeepToneManager
import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class StretchingRoutineViewModelTest : KoinTest {

    private val viewModel: StretchingRoutineViewModel by inject()
    private val beepToneManager: FakeBeepToneManager by inject<BeepToneManager>() as Lazy<FakeBeepToneManager>
    private val textToSpeechManager: FakeTextToSpeechManager by inject<TextToSpeechManager>() as Lazy<FakeTextToSpeechManager>
    private val localeManager: FakeLocaleManager by inject<LocaleManager>() as Lazy<FakeLocaleManager>
    private val stretchingRoutineRepository: FakeStretchingRoutineRepository by inject<StretchingRoutineRepository>() as Lazy<FakeStretchingRoutineRepository>

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single<BeepToneManager> { FakeBeepToneManager() }
                single<TextToSpeechManager> { FakeTextToSpeechManager() }
                single<LocaleManager> { FakeLocaleManager() }
                single<StretchingRoutineRepository> { FakeStretchingRoutineRepository() }
                single { StretchingRoutineViewModel(get(), get(), get(), get()) }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        val initialState = StretchingRoutineUiState()
        assertEquals(initialState, viewModel.uiState.value)
    }
}
