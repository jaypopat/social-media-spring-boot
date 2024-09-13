FROM oven/bun:1 AS base

WORKDIR /app

COPY bun.lockb package.json ./

RUN bun install --frozen-lockfile

COPY . .

RUN bun run build

EXPOSE 5173

CMD ["bun", "run", "dev"]
