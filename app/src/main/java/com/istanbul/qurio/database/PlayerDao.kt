package com.istanbul.qurio.database

import androidx.room.*
import com.istanbul.qurio.model.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayer(player: Player)

    @Query("SELECT * FROM player LIMIT 1")
    suspend fun getPlayer(): Player?

    @Query("UPDATE player SET lives = lives + 1, coins = coins - 200")
    suspend fun buyLife()

    @Query("UPDATE player SET lives = lives - 1 WHERE lives > 0")
    suspend fun loseLife()

    @Query("SELECT lives FROM player LIMIT 1")
    suspend fun getNumberOfLife(): Int
}