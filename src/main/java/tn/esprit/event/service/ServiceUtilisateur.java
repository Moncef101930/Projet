package tn.esprit.event.service;

import tn.esprit.event.entity.Role;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur implements IService<Utilisateur>{
    private Connection cnx;
    public ServiceUtilisateur(){
        cnx= MyConnection.getInstance().getCnx();
    }
    @Override
    public void ajouter(Utilisateur utilisateur) {
        try{
            String query="INSERT INTO `utilisateur`" +
                    "(`nom`, `prenom`, `email`, `mot_de_passe`," +
                    " `role`, `date_naissance`, `bio`, `image`)" +
                    " VALUES ('"+utilisateur.getNom()+"','"+utilisateur.getPrenom()+"'," +
                    "'"+utilisateur.getEmail()+"','"+utilisateur.getMot_de_passe().replace("$2a$","$2y$")+"'," +
                    "'"+utilisateur.getRole().name()+"','"+utilisateur.getDate_naissance()+"'," +
                    "'"+utilisateur.getBio()+"','"+utilisateur.getImage()+"')";
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(int id, Utilisateur utilisateur) {
        try{
            String query="UPDATE `utilisateur` SET " +
                    "`nom`='"+utilisateur.getNom()+"',`prenom`='"+utilisateur.getPrenom()+"'," +
                    "`email`='"+utilisateur.getEmail()+"',`mot_de_passe`='"+utilisateur.getMot_de_passe().replace("$2a$","$2y$")+"'," +
                    "`role`='"+utilisateur.getRole().name()+"',`date_naissance`='"+utilisateur.getDate_naissance()+"'," +
                    "`bio`='"+utilisateur.getBio()+"',`image`='"+utilisateur.getImage()+"' WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try{
            String query="DELETE FROM `utilisateur` WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Utilisateur> afficher() {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        try{
            String query="SELECT * FROM `utilisateur`";
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Utilisateur utilisateur=new Utilisateur();
                utilisateur.setId(rs.getLong("id"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setBio(rs.getString("bio"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setImage(rs.getString("image"));
                utilisateur.setMot_de_passe(rs.getString("mot_de_passe"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setRole(Role.valueOf(rs.getString("role")));
                utilisateur.setDate_naissance(rs.getDate("date_naissance").toLocalDate());
                utilisateurs.add(utilisateur);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return utilisateurs;
    }
    public Utilisateur login(String email, String password) {
        Utilisateur user = null;
        password=password.replace("$2y$","$2a$");
        try {
            String query = "SELECT * FROM `utilisateur` WHERE email='" + email +
                    "' AND mot_de_passe='" + password + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                user = new Utilisateur();
                user.setId(rs.getLong("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMot_de_passe(rs.getString("mot_de_passe"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setDate_naissance(rs.getDate("date_naissance").toLocalDate());
                user.setBio(rs.getString("bio"));
                user.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    public Utilisateur findByEmail(String email) {
        return afficher().stream().filter(u->u.getEmail().equals(email)).findFirst().orElse(null);
    }
    public boolean updatePasswordByEmail(String email, String newPassword) {
        String query = "UPDATE utilisateur SET mot_de_passe = ? WHERE email = ?";
        try (
             PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, newPassword.replace("$2a$","$2y$"));
            ps.setString(2, email);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
