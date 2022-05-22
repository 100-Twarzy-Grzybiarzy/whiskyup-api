resource "aws_ecs_cluster" "cluster" {
  name = "${var.prefix}-cluster"
}

resource "aws_ecs_task_definition" "task" {
  family                = "${var.prefix}-task"
  container_definitions = jsonencode([
    {
      name         = var.containers[0].name
      image        = var.containers[0].image
      cpu          = 10
      memory       = 256
      essential    = true
      portMappings = [
        {
          containerPort = 80
          hostPort      = 80
          protocol      = "tcp"
        }
      ]
    }, {
      name      = var.containers[1].name
      image     = var.containers[1].image
      cpu       = 10
      memory    = 128
      essential = true
    }, {
      name      = var.containers[2].name
      image     = var.containers[2].image
      cpu       = 10
      memory    = 128
      essential = true
    }
  ])

  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  memory                   = 512
  cpu                      = 30
  execution_role_arn       = var.role_arn

  volume {
    name      = "service-storage"
    host_path = "/ecs/service-storage"
  }
}

resource "aws_ecs_service" "service" {
  name            = "${var.prefix}-service"
  cluster         = aws_ecs_cluster.cluster.id
  task_definition = aws_ecs_task_definition.task.arn
  launch_type     = "FARGATE"
  desired_count   = 1
}