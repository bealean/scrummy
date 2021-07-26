<template>
  <div id="overlay">
    <div id="loading-div">
      <div id="timer">
        <div id="done-dot"></div>
        <div id="clock">
          <div id="clock-hand"></div>
        </div>
        <div id="timer-base"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
#overlay {
  position: fixed;
  display: block;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.5);
  z-index: 2;
}

#loading-div > div {
  position: absolute;
  top: 33%;
  left: 50%;
  /* Adjust position for half of the div's width and height
  so the position is relative to the center of the timer. */
  transform: translate(-50%, -50%);
}

#timer {
  width: 200px;
  height: 215px;
  background-color: #94c973;
  border-top-left-radius: 50%;
  border-top-right-radius: 50%;
}

#timer-base {
  width: 210px;
  height: 20px;
  background-color: #94c973;
  position: absolute;
  top: 215px;
  left: -5px;
  border-radius: 15px;
}

#done-dot {
  width: 10px;
  height: 10px;
  background-color: #e76f51;
  border-radius: 50%;
  position: absolute;
  top: 10px;
  left: 95px;
}

@keyframes tick {
  12.5% {
    transform: rotate(315deg);
  }
  25% {
    transform: rotate(270deg);
  }
  37.5% {
    transform: rotate(225deg);
  }
  50% {
    transform: rotate(180deg);
  }
  62.5% {
    transform: rotate(135deg);
  }
  75% {
    transform: rotate(90deg);
  }
  87.5% {
    transform: rotate(45deg);
  }
  100% {
    transform: rotate(0deg);
  }
}

#clock {
  width: 160px;
  height: 160px;
  background-color: #ffffff;
  border-radius: 50%;
  position: absolute;
  top: 25px;
  left: 15px;
  border-style: dotted;
  border-color: #000000;
  border-width: 5px;
  transform: rotate(360deg);
  animation: tick 1s infinite;
}

#clock-hand {
  width: 20px;
  height: 110px;
  background-color: #ffffff;
  position: absolute;
  top: 20px;
  left: 65px;
  border-color: lightgray;
  border-width: 5px;
  border-style: outset;
}
</style>