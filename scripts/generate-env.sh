#!/usr/bin/env sh

# .env.example 생성
cat > .env.example << 'EOF'
# Server
PORT=8080

# API
API_PREFIX=api
API_VERSION=v1

# Database
DATABASE_URL=jdbc:postgresql://localhost:5432/database
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

# Logging
LOGGING_SQL_SHOW=true
LOGGING_SQL_LEVEL=trace

# JWT
JWT_SECRET_KEY=
JWT_HEADER=Authorization
JWT_PREFIX="Bearer "
JWT_ISSUER=
JWT_ACCESS_EXPIRES_IN=3600
JWT_REFRESH_EXPIRES_IN=604800
EOF

echo ".env.example 파일이 생성되었습니다."
