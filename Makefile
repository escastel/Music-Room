help:
	@echo "Usage: make [rule]"
	@echo ""
	@echo "  all      Build and start all services"
	@echo "  clean    Stop running containers"
	@echo "  fclean   Stop containers and remove them + networks"
	@echo "  re       fclean + all"
	@echo "  nuke     Remove everything: containers, volumes, images and orphans"

all:
	BUILDKIT_PROGRESS=plain docker compose up --build -d --quiet-pull
clean:
	docker compose stop

fclean:
	docker compose down

re: fclean all

nuke:
	docker compose down --volumes --rmi all --remove-orphans

.PHONY: all clean fclean re nuke help
