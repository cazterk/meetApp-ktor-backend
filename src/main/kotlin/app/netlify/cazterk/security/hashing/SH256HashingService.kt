package app.netlify.cazterk.security.hashing

import java.security.SecureRandom

class SH256HashingService : HashingService {
    override fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)

    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        TODO("Not yet implemented")
    }
}