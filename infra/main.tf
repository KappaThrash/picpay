terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "6.17.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_security_group" "security_Group" {
  name = "security_Group"
  description = "Acesso HTTP e Internet"

  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_key_pair" "keypair" {
  key_name = "terraform_keypair"
  public_key = file("C:/Users/Daniel/.ssh/id_ed25519.pub")
}

resource "aws_instance" "server_Ec2" {
  ami = "ami-00e801948462f718a"
  instance_type = "t3.micro"
  user_data = file("user_data.sh")
  key_name = aws_key_pair.keypair.key_name
  vpc_security_group_ids = [aws_security_group.security_Group.id]

  provisioner "remote-exec" {
    inline = ["mkdir -p /home/ec2-user/picpay"]

    connection {
      type = "ssh"
      user = "ec2-user"
      private_key = file("C:/Users/Daniel/.ssh/id_ed25519")
      host = self.public_ip
    }
  }

  provisioner "file" {
    source = "docker-compose.yml"
    destination = "/home/ec2-user/picpay/docker-compose.yml"

    connection {
      type = "ssh"
      user = "ec2-user"
      private_key = file("C:/Users/Daniel/.ssh/id_ed25519")
      host = self.public_ip
    }
  }

  provisioner "file" {
    source = ".env"
    destination = "/home/ec2-user/picpay/.env"

    connection {
      type = "ssh"
      user = "ec2-user"
      private_key = file("C:/Users/Daniel/.ssh/id_ed25519")
      host = self.public_ip
    }
  }

  provisioner "file" {
    source      = "start.sh"
    destination = "/home/ec2-user/picpay/start.sh"

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("C:/Users/Daniel/.ssh/id_ed25519")
      host        = self.public_ip
    }
  }

  provisioner "remote-exec" {
    inline = [
      "chmod +x /home/ec2-user/picpay/start.sh",
      "/home/ec2-user/picpay/start.sh"
    ]

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("C:/Users/Daniel/.ssh/id_ed25519")
      host        = self.public_ip
    }
  }
}

