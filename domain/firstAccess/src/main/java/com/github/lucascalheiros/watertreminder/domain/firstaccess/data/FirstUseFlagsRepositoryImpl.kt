package com.github.lucascalheiros.watertreminder.domain.firstaccess.data

import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.FirstUseFlagsDao
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.repositories.FirstUseFlagsRepository

class FirstUseFlagsRepositoryImpl(
    private val dao: FirstUseFlagsDao
): FirstUseFlagsRepository {
    override suspend fun isDailyIntakeFirstSet(): Boolean {
        return dao.isDailyIntakeFirstSet()
    }

    override suspend fun isFirstAccessCompleted(): Boolean {
        return dao.isFirstAccessCompleted()
    }

    override suspend fun setDailyIntakeSetFlag(state: Boolean) {
        dao.setDailyIntakeSetFlag(state)
    }

    override suspend fun setFirstAccessCompletedFlag(state: Boolean) {
        dao.setFirstAccessCompletedFlag(state)
    }
}