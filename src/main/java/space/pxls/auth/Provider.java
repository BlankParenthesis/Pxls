package space.pxls.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Map;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class Provider {
    public final String userName;    
    public final String userId;    
    public final String identityProvider;

    public Provider(String userName, String userId, String identityProvider) {
        this.userName = userName;
        this.userId = userId;
        this.identityProvider = identityProvider;
    }

    public static Optional<Provider> fromMap(Map json) {
        final Object maybeName = json.get("username");
        final Object maybeId = json.get("id");
        final Object maybeProvider = json.get("service");
        if (maybeName instanceof String && maybeId instanceof String && maybeProvider instanceof String) {
            final String userName = (String) maybeName;
            final String userId = (String) maybeId;
            final String identityProvider = (String) maybeProvider;

            return Optional.of(new Provider(userName, userId, identityProvider));
        } else {
            return Optional.empty();
        }
    }

    public static class Mapper implements RowMapper<Provider> {
        @Override
        public Provider map(ResultSet r, StatementContext ctx) throws SQLException {
            return new Provider(
                r.getString("user_name"),
                r.getString("user_id"),
                r.getString("identity_provider")
            );
        }
    }
}
