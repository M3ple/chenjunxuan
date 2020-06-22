$(function () {
  $.ajax({
    type: "GET",
    url: "/selectAll",
    dataType: "json",
    success: function (resumes) {
      //成功的处理
      $.each(resumes, function (index, resume) {
        $("#t_body").append(
            "<tr name=" + resume.id + "><td>" + resume.id + "</td>" +
            "<td>" + resume.name + "</td>" +
            "<td>" + resume.phone + "</td>" +
            "<td>" + resume.address + "</td>" +
            "<td><a href='javascript:' onclick ='updateData("
            + resume.id + ")'>编辑</a>/<a href='javascript:' onclick ='delData("
            + resume.id + ")'>删除</a></td></tr>");
      });
    },
    error: function (data) {
      if (data.responseText == 'loseSession') {
        //在这个地方进行跳转
        window.location.href = "http://localhost:8080/login.jsp";
      }
    }
  });

  $("#btn_saveInfo").click(function () {
    saveData();
  });
});

function saveData() {
//拿到上面文本框中的内容
  var name = $("#t_name").val();
  var tel = $("#t_phone").val();
  var address = $("#t_address").val();
  $.ajax({
    url: "/update",
    type: "post",
    dataType: "json",
    contentType: 'application/json',
    cache: false,
    data: JSON.stringify({
      name: name,
      phone: tel,
      address: address
    }),
    success: function (responseData) {
      if (200 == responseData.code) {
        alert("添加成功!");
        window.location.reload();
      } else {
        alert("添加失败!");
      }
    },
    error: function (responseData) {
      alert("添加失败!");
    }
  });
}

function updateData(id) {
  //拿到上面文本框中的内容
  var name = $("#t_name").val();
  var tel = $("#t_phone").val();
  var address = $("#t_address").val();
  $.ajax({
    url: "/update",
    type: "post",
    dataType: "json",
    contentType: 'application/json',
    cache: false,
    data: JSON.stringify({
      id: id,
      name: name,
      phone: tel,
      address: address
    }),
    success: function (responseData) {
      if (200 == responseData.code) {
        alert("修改成功!");
        window.location.reload();
      } else {
        alert("修改失败!");
      }
    },
    error: function (responseData) {
      alert("修改失败!");
    }
  });
}

function delData(id) {
  $.post("/delete", {'id': id}, function (responseData) {
    if (200 == responseData.code) {
      alert("删除成功！");
      window.location.reload();
    } else {
      alert("删除失败!");
    }
  }, "json");
}