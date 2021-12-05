package com.cedricakrou.artisanat.domain.account.worker

import com.b2i.neo.application.controlForm.Generate
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.vm.UserVm
import com.cedricakrou.artisanat.data.repositories.UserRepository
import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.domain.speciality.worker.SpecialityDomain
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class UserWorker(private val userRepository: UserRepository,
                 val specialityDomain: SpecialityDomain,
                 val eventPublisher: ApplicationEventPublisher,
) : UserDomain {

    override fun save(user: User): OperationResult<User> {

        var userSave = user

        val errors : HashMap<String, String> = HashMap<String, String>()

        if ( user.firstname.isEmpty() ) {
            errors["firstnameEmpty"] = "Veuillez renseigner votre nom"
        }

        if ( user.lastname.isEmpty() ) {
            errors["lastnameEmpty"] = "Veuillez renseigner votre prenom"
        }

        if ( user.username.isEmpty() ) {
            errors["usernameIsEmpty"] = "Veuillez renseigner votre username"
        }

        if ( user.phoneNumber.isEmpty() ) {
            errors["phoneNumberEmpty"] = "Veuillez renseigner votre numéro de téléphone"
        }

        if ( user.email.isEmpty() ) {
            errors["emailEmpty"] = "Veuillez renseigner votre email"
        }

        if (user.id <= 0L) {

            if ( userRepository.countByUsername( user.username ) != 0L && user.id == -1L  ) {
                errors.put("usernameExists", "Cet username est dejà utilisé" )
            }

            if ( userRepository.countByEmail( user.email ) != 0L && user.id == -1L ) {
                errors.put("emailExists", "Cet Email est dejà utilisé" )
            }

        }

        if ( user is Artisan) {

            if ( user.speciality != null && user.speciality!!.id != -1L ) {



                val speciality = specialityDomain.findById( user.speciality!!.id ).orElse( null )

                if ( speciality == null ) {
                    errors["specialityNotExists"] = "Cette specialité n'existe pas"
                }
                else {
                    user.speciality = speciality
                }

            }
            else {
                errors["chooseSpeciality"] = "Veuillez choisir une specialité svp"
            }

        }


        if ( errors.isEmpty() ) {

            try {

                if ( user.password.isEmpty() ) {
                    user.password = Generate.generatedRandomCharacter( 6 )
//                    eventPublisher.publishEvent( RegisterDoneEvent( user ) )
                }

                userSave = userRepository.save(user)

            }
            catch ( ex : Exception ) {
                errors["verifyNetwork"] = "Veuillez verifier votre connexion internet"
            }

        }


        return OperationResult( errors, userSave )
    }

    override fun findById(id: Long): Optional<User> = userRepository.findById(id)

    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)
    override fun findByEmail(email: String): Optional<User> = userRepository.findByEmail(email)
    override fun findTypeBy(id: Long): String = userRepository.findTypeBy(id)

    override fun count(): Long = userRepository.count()

    override fun changeStatut(user: User): OperationResult<User> {

        val errors : HashMap<String, String> = HashMap<String, String>()


        if ( user.username.isEmpty() ) {
            errors.put("username", "Veuillez renseigner le username" )
        }

        if ( errors.isEmpty() ){

            var userFind = userRepository.findByUsername( user.username  ).orElse(null)

            if ( userFind == null ) {
                errors.put("userNotExists", "L'utilisateur ${user.username} n'existe pas dans djepay" )
            }
            else {

                userFind.enabled = !userFind.enabled

                userRepository.save( userFind )

            }

        }

        return OperationResult( errors, user )
    }

    override fun savePin(user: User): OperationResult<User> {

        val errors : HashMap<String, String> = HashMap<String, String>()


        if ( user.username.isEmpty() ) {
            errors.put("username", "Veuillez renseigner le username" )
        }

        if ( user.pin.isEmpty() ) {
          errors["pinEmpty"] = "Veuillez enregistrer votre code pin."
        }

        if( user is Artisan) {

            val speciality : Speciality? = specialityDomain.findById( user.speciality!!.id ).orElse(null)

            if ( speciality == null ) {
                errors["specialityNotExist"] = "La specialité est introuvable"
            }
            else {
                user.speciality = speciality
            }

        }


        if ( errors.isEmpty() ){

            var userFind = userRepository.findByUsername( user.username  ).orElse(null)

            if ( userFind == null ) {
                errors.put("userNotExists", "L'utilisateur ${user.username} n'existe pas dans djepay" )
            }
            else {

                if ( BCryptPasswordEncoder().matches( user.pin, userFind.pin ) ) {
                    errors["samePin"] = "Veuillez saisir un code pin different du précedent"
                }
                else {

                    userFind.pin = BCryptPasswordEncoder().encode(user.pin)

                    userRepository.save( userFind )
                }

            }

        }

        return OperationResult( errors, user )

    }

    override fun changePassword(userVm: UserVm): OperationResult<UserVm> {

        var user : User? = userVm.user
        val errors : HashMap<String, String> = HashMap()

        if ( user!!.username.isEmpty() ) {
            errors["userNoExists"] = "Veuillez choisir un user correct"
        }

        if ( user.username.isNotEmpty() ) {

            user = userRepository.findByUsername( user.username ).orElse(null)

            if ( user == null ) {
                errors["userNoExists"] = "L'utilisateur n'existe pas dans Djêpay"
            }
        }


        if ( !userVm.oldPassword.equals("-1") && BCryptPasswordEncoder().matches( userVm.oldPassword, user!!.password ) ) {
            errors["oldPasswordNotCorrect"] = "Votre ancien mot de passe n'est pas correct"
        }

        if ( userVm.newPassword.isEmpty() ) {
            errors["newPasswordEmpty"] = "Veuillez renseigner le nouveau mot de passe"
        }

        if ( userVm.newPassword != userVm.confirmPassword ) {
            errors["passwordAndConfirmationNotSame"] = "Le nouveau mot de passe et la confirmation ne sont pas identique"
        }

        if ( errors.isEmpty() ) {

            user!!.password = BCryptPasswordEncoder().encode( userVm.newPassword )

            if ( user.firstConnection ) {
                user.firstConnection = false
                user.enabled = true
            }

            userRepository.save( user )

        }

        return  OperationResult( errors, userVm )
    }

    override fun saveData(user: User) {
        userRepository.save( user )
    }

    override fun findByReference(reference: String): Optional<User> = userRepository.findByReference( reference )

    override fun countAllByReference(reference: String): Long = userRepository.countAllByReference( reference )
}