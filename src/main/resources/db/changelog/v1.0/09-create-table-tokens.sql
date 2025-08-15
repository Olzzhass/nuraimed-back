-- Create tokens table
CREATE TABLE tokens (
    id BIGSERIAL PRIMARY KEY,
    token TEXT NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    expired BOOLEAN DEFAULT FALSE,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create index on token for faster lookups
CREATE INDEX idx_tokens_token ON tokens(token);
-- Create index on user_id for faster user-related queries
CREATE INDEX idx_tokens_user_id ON tokens(user_id);
