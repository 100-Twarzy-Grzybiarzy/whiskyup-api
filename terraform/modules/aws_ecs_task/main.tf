resource "aws_ecs_cluster" "cluster" {
  name = "${var.prefix}-cluster"
}

resource "aws_ecs_task_definition" "task" {
  family                = "${var.prefix}-task"
  container_definitions = jsonencode([
    {
      name         = var.containers[0].name
      image        = var.containers[0].image
      essential    = true
      portMappings = [
        {
          containerPort = 80
          hostPort      = 80
          protocol      = "tcp"
        }
      ],
      environment = var.containers[0].environment
    }, {
      name        = var.containers[1].name
      image       = var.containers[1].image
      essential   = true
      environment = var.containers[1].environment

    }, {
      name        = var.containers[2].name
      image       = var.containers[2].image
      essential   = true
      environment = var.containers[2].environment

    }
  ])

  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  memory                   = 512
  cpu                      = 256
  execution_role_arn       = var.role_arn
}

resource "aws_ecs_service" "service" {
  name            = "${var.prefix}-service"
  cluster         = aws_ecs_cluster.cluster.id
  task_definition = aws_ecs_task_definition.task.arn
  launch_type     = "FARGATE"
  desired_count   = 1
  tags            = var.tags

  network_configuration {
    subnets          = [aws_default_subnet.default_subnet_a.id]
    assign_public_ip = true
  }
}

resource "aws_default_vpc" "default_vcp" {
}

resource "aws_default_subnet" "default_subnet_a" {
  availability_zone = "${var.aws_region}a"
}