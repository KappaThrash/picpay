package bank.picpay.repository;

import bank.picpay.models.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
}
