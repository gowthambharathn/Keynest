package skynetbee.gowtham.keynest.data.repository

/**
 * Created by Gowtham Barath
 * Date: 05-07-2026
 */

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import skynetbee.gowtham.keynest.data.local.PasswordDao
import skynetbee.gowtham.keynest.data.local.PasswordEntity
import skynetbee.gowtham.keynest.domain.model.Password
import skynetbee.gowtham.keynest.domain.repository.PasswordRepository
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao
) : PasswordRepository {

    override suspend fun insert(
        password: Password
    ) {

        passwordDao.insert(
            PasswordEntity(
                id = password.id,
                title = password.title,
                encryptedPassword = password.encryptedPassword,
                iv = password.iv,
                createdAt = password.createdAt
            )
        )

    }

    override fun getPasswords(): Flow<List<Password>> {

        return passwordDao
            .getPasswords()
            .map { list ->

                list.map {

                    Password(
                        id = it.id,
                        title = it.title,
                        encryptedPassword = it.encryptedPassword,
                        iv = it.iv,
                        createdAt = it.createdAt
                    )

                }

            }

    }

    override suspend fun delete(
        id: Long
    ) {

        passwordDao.delete(id)

    }
}